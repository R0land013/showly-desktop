package io.github.r0land013.showly.desktop.model;

import io.github.r0land013.showly.slides.Slide;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class FxSlide {
    
    private int slideIndex;
    private Image image;

    public FxSlide(Slide slide) {
        slideIndex = slide.getSlideIndex();
        image = SwingFXUtils.toFXImage(slide.getImageStream(), null);;
    }


    public int getSlideIndex() {
        return slideIndex;
    }

    public Image getImage() {
        return image;
    }
}
