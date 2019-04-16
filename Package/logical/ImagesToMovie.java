package logical;

import java.io.*;
import java.util.*;
import java.awt.Dimension;
import javax.media.*;
import javax.media.control.*;
import javax.media.protocol.*;
import javax.media.protocol.DataSource;
import javax.media.datasink.*;
import javax.media.format.VideoFormat;

/**
 * This program takes a list of JPEG image files and convert them into a
 * QuickTime movie.
 */
public class ImagesToMovie implements ControllerListener, DataSinkListener
{

    /**
     *
     * Takes a list of JPEG image files and convert them into a
     * video file.
     * 
     * @param width Width of images.
     * @param height Height of images.
     * @param frameRate Frame rate of the video.
     * @param inFiles List of images.
     * @param outML Output Location of video file. 
     * @return true if success else false.
     * @throws IOException
     * @throws NoProcessorException
     * @throws NoDataSinkException
     * @throws InterruptedException
     */
    public boolean doIt(int width, int height, int frameRate, ArrayList inFiles, MediaLocator outML) throws IOException, NoProcessorException, NoDataSinkException, InterruptedException
    {
        ImageDataSource ids = new ImageDataSource(width, height, frameRate, inFiles);
        Processor p;
        
        p = Manager.createProcessor(ids);
        p.addControllerListener(this);

        // Put the Processor into configured state so we can set
        // some processing options on the processor.
        p.configure();
        if (!waitForState(p, Processor.Configured))
        {
            return false;
        }

        // Set the output content descriptor to QuickTime. 
        p.setContentDescriptor(new ContentDescriptor(FileTypeDescriptor.QUICKTIME));

        // Query for the processor for supported formats.
        // Then set it on the processor.
        TrackControl tcs[] = p.getTrackControls();
        Format f[] = tcs[0].getSupportedFormats();
        if (f == null || f.length <= 0)
        {

            return false;
        }

        tcs[0].setFormat(f[0]);

        // We are done with programming the processor.  Let's just
        // realize it.
        p.realize();
        if (!waitForState(p, Processor.Realized))
        {

            return false;
        }

        // Now, we'll need to create a DataSink.
        DataSink dsink;
        if ((dsink = createDataSink(p, outML)) == null)
        {

            return false;
        }

        dsink.addDataSinkListener(this);
        fileDone = false;

        // OK, we can now start the actual transcoding.
        p.start();
        dsink.start();

        // Wait for EndOfStream event.
        waitForFileDone();

        // Cleanup.
        dsink.close();

        p.removeControllerListener(this);

        return true;
    }

    /**
     * Create the DataSink.
     */
    DataSink createDataSink(Processor p, MediaLocator outML) throws NoDataSinkException, IOException
    {

        DataSource ds;

        if ((ds = p.getDataOutput()) == null)
        {

            return null;
        }

        DataSink dsink;

        dsink = Manager.createDataSink(ds, outML);
        dsink.open();

        return dsink;
    }

    final Object waitSync = new Object();
    boolean stateTransitionOK = true;

    /**
     * Block until the processor has transitioned to the given state. Return
     * false if the transition failed.
     */
    boolean waitForState(Processor p, int state)
    {
        synchronized (waitSync)
        {
            try
            {
                while (p.getState() < state && stateTransitionOK)
                {
                    waitSync.wait();
                }
            } catch (InterruptedException e)
            {
            }
        }
        return stateTransitionOK;
    }

    /**
     * Controller Listener.
     * @param evt
     */
    @Override
    public void controllerUpdate(ControllerEvent evt)
    {

        if (evt instanceof ConfigureCompleteEvent
                || evt instanceof RealizeCompleteEvent
                || evt instanceof PrefetchCompleteEvent)
        {
            synchronized (waitSync)
            {
                stateTransitionOK = true;
                waitSync.notifyAll();
            }
        } else if (evt instanceof ResourceUnavailableEvent)
        {
            synchronized (waitSync)
            {
                stateTransitionOK = false;
                waitSync.notifyAll();
            }
        } else if (evt instanceof EndOfMediaEvent)
        {
            evt.getSourceController().stop();
            evt.getSourceController().close();
        }
    }

    final Object waitFileSync = new Object();
    boolean fileDone = false;
    boolean fileSuccess = true;

    /**
     * Block until file writing is done.
     */
    boolean waitForFileDone() throws InterruptedException
    {
        synchronized (waitFileSync)
        {

            while (!fileDone)
            {
                waitFileSync.wait();
            }

        }
        return fileSuccess;
    }

    /**
     * Event handler for the file writer.
     * @param evt
     */
    @Override
    public void dataSinkUpdate(DataSinkEvent evt)
    {

        if (evt instanceof EndOfStreamEvent)
        {
            synchronized (waitFileSync)
            {
                fileDone = true;
                waitFileSync.notifyAll();
            }
        } else if (evt instanceof DataSinkErrorEvent)
        {
            synchronized (waitFileSync)
            {
                fileDone = true;
                fileSuccess = false;
                waitFileSync.notifyAll();
            }
        }
    }

    ///////////////////////////////////////////////
    //
    // Inner classes.
    ///////////////////////////////////////////////
    /**
     * A DataSource to read from a list of JPEG image files and turn that into a
     * stream of JMF buffers. The DataSource is not seekable or positionable.
     */
    class ImageDataSource extends PullBufferDataSource
    {

        ImageSourceStream streams[];

        ImageDataSource(int width, int height, int frameRate, ArrayList images)
        {
            streams = new ImageSourceStream[1];
            streams[0] = new ImageSourceStream(width, height, frameRate, images);
        }

        @Override
        public void setLocator(MediaLocator source)
        {
        }

        @Override
        public MediaLocator getLocator()
        {
            return null;
        }

        /**
         * Content type is of RAW since we are sending buffers of video frames
         * without a container format.
         */
        @Override
        public String getContentType()
        {
            return ContentDescriptor.RAW;
        }

        @Override
        public void connect()
        {
        }

        @Override
        public void disconnect()
        {
        }

        @Override
        public void start()
        {
        }

        @Override
        public void stop()
        {
        }

        /**
         * Return the ImageSourceStreams.
         */
        @Override
        public PullBufferStream[] getStreams()
        {
            return streams;
        }

        /**
         * We could have derived the duration from the number of frames and
         * frame rate. But for the purpose of this program, it's not necessary.
         */
        @Override
        public Time getDuration()
        {
            return DURATION_UNKNOWN;
        }

        @Override
        public Object[] getControls()
        {
            return new Object[0];
        }

        @Override
        public Object getControl(String type)
        {
            return null;
        }
    }

    /**
     * The source stream to go along with ImageDataSource.
     */
    class ImageSourceStream implements PullBufferStream
    {

        ArrayList images;
        int width, height;
        VideoFormat format;

        int nextImage = 0;  // index of the next image to be read.
        boolean ended = false;

        public ImageSourceStream(int width, int height, int frameRate, ArrayList images)
        {
            this.width = width;
            this.height = height;
            this.images = images;

            format = new VideoFormat(VideoFormat.JPEG,
                    new Dimension(width, height),
                    Format.NOT_SPECIFIED,
                    Format.byteArray,
                    (float) frameRate);
        }

        /**
         * We should never need to block assuming data are read from files.
         */
        @Override
        public boolean willReadBlock()
        {
            return false;
        }

        /**
         * This is called from the Processor to read a frame worth of video
         * data.
         */
        @Override
        public void read(Buffer buf) throws IOException
        {

            // Check if we've finished all the frames.
            if (nextImage >= images.size())
            {
                // We are done.  Set EndOfMedia.

                buf.setEOM(true);
                buf.setOffset(0);
                buf.setLength(0);
                ended = true;
                return;
            }

            String imageFile = (String) images.get(nextImage);
            nextImage++;

            // Open a random access file for the next image. 
            RandomAccessFile raFile;
            raFile = new RandomAccessFile(imageFile, "r");

            byte data[] = null;

            // Check the input buffer type & size.
            if (buf.getData() instanceof byte[])
            {
                data = (byte[]) buf.getData();
            }

            // Check to see the given buffer is big enough for the frame.
            if (data == null || data.length < raFile.length())
            {
                data = new byte[(int) raFile.length()];
                buf.setData(data);
            }

            // Read the entire JPEG image from the file.
            raFile.readFully(data, 0, (int) raFile.length());

            buf.setOffset(0);
            buf.setLength((int) raFile.length());
            buf.setFormat(format);
            buf.setFlags(buf.getFlags() | Buffer.FLAG_KEY_FRAME);

            // Close the random access file.
            raFile.close();
        }

        /**
         * Return the format of each video frame. That will be JPEG.
         */
        @Override
        public Format getFormat()
        {
            return format;
        }

        @Override
        public ContentDescriptor getContentDescriptor()
        {
            return new ContentDescriptor(ContentDescriptor.RAW);
        }

        @Override
        public long getContentLength()
        {
            return 0;
        }

        @Override
        public boolean endOfStream()
        {
            return ended;
        }

        @Override
        public Object[] getControls()
        {
            return new Object[0];
        }

        @Override
        public Object getControl(String type)
        {
            return null;
        }
    }

}
