package Model;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FilterEngine 
{
    private BufferedImage RasterImage(final byte[] pixels, final int imageWidth, final int imageHeight) 
    {
	BufferedImage returnImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_4BYTE_ABGR);
        returnImage.setData(Raster.createRaster(returnImage.getSampleModel(), new DataBufferByte(pixels, pixels.length), null));
	return returnImage;
    }

    public BufferedImage processImage(final BufferedImage image, Filter filter) 
    {
	BufferedImage img = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        img.getGraphics().drawImage(image, 0, 0, null);
	final int Width = img.getWidth();
	final int Height = img.getHeight();
        
	final byte[] pixels = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
	final byte[] newPixels = new byte[pixels.length];

	ExecutorService processImageMultithread = Executors.newFixedThreadPool(1);
	CompletionService<Void> processImageMultithreadCompletition = new ExecutorCompletionService<Void>(processImageMultithread);

	for (int i = 0; i < 1; i++) 
        {
            final int indexer = i;
            processImageMultithreadCompletition.submit(new Callable<Void>() 
            {
                public Void call() 
                {
                    final int startRow = indexer * Height;
                    final int endRow = (indexer + 1) * Height;
                    final int startCol = 0;
                    final int endCol = Width;

                    switch (filter) 
                    {
			case BLUE:
                            for (int i = startRow; i < endRow; i++) 
                            {
                                for (int j = startCol; j < endCol; j++) 
                                {
                                    int pos = i * 4 * Width + j * 4;
                                    newPixels[pos] = (byte) 0xFF;
                                    newPixels[pos + 1] = pixels[pos + 1];
                                }
                            }
                            break;
                            
			case GREEN:
                            for (int i = startRow; i < endRow; i++) 
                            {
                                for (int j = startCol; j < endCol; j++) 
                                {
                                    int pos = i * 4 * Width + j * 4;
                                    newPixels[pos] = (byte) 0xFF;
                                    newPixels[pos + 2] = pixels[pos + 2];
                                }
                            }
                            break;
                            
			case RED:
                            for (int i = startRow; i < endRow; i++) 
                            {
                                for (int j = startCol; j < endCol; j++) 
                                {
                                    int pos = i * 4 * Width + j * 4;
                                    newPixels[pos] = (byte) 0xFF;
                                    newPixels[pos + 3] = pixels[pos + 3];
                                }
                            }
                            break;
                            
			case GRAYSCALE:
                            for (int i = startRow; i < endRow; i++) 
                            {
                                for (int j = startCol; j < endCol; j++) 
                                {
                                    int pos = i * 4 * Width + j * 4;

                                    final float blue = ((int) pixels[pos + 1]) & 0xFF;
                                    final float green = ((int) pixels[pos + 2]) & 0xFF;
                                    final float red = ((int) pixels[pos + 3]) & 0xFF;
                                    byte gray = (byte) (0.299f * red + 0.587f * green + 0.114f * blue);
                                    newPixels[pos] = (byte) 0xFF;
                                    for (int pixelNumber = 1; pixelNumber < 4; pixelNumber++) newPixels[pos + pixelNumber] = gray;
                                }
                            }
                            break;
                            
			case NEGATIVE:
                            for (int i = startRow; i < endRow; i++) 
                            {
                                for (int j = startCol; j < endCol; j++) 
                                {
                                    int pos = i * 4 * Width + j * 4;
                                    newPixels[pos] = (byte) 0xFF;
                                    for (int pixelNumber = 1; pixelNumber < 4; ++pixelNumber) 
                                        newPixels[pos + pixelNumber] = (byte) (255 - (int) (pixels[pos + pixelNumber] & 0xFF));
                                }
                            }
                            break;
                            
                        default:
                            break;
                    }
                    return null;
		}
            });
	}

	for (int i = 0; i < 1; i++) 
        {
            try 
            {
		processImageMultithreadCompletition.take();
            } 
            catch (InterruptedException e) {}
	}
        return RasterImage(newPixels, Width, Height);
    }
}