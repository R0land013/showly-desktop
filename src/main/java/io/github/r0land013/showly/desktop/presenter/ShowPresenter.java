package io.github.r0land013.showly.desktop.presenter;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import io.github.r0land013.showly.Showly;
import io.github.r0land013.showly.ShowlyConfig;
import io.github.r0land013.showly.desktop.model.FxSlide;
import io.github.r0land013.showly.desktop.view.AbstractView;
import io.github.r0land013.showly.desktop.view.ShowView;
import io.github.r0land013.showly.slides.Slide;


public class ShowPresenter extends AbstractPresenter {
    
    static String SHOWLY_CONFIG_KEY = "showlyConfig";
    static String SHOWLY_INSTANCE_KEY = "showlyInstance";
    static String SLIDE_LIST_KEY = "slideList";

    private ShowView view;
    private Showly showly;
    private ShowlyConfig config;
    private int currentSlideIndex;
    private List<FxSlide> slideList;

    public ShowPresenter(ApplicationManager appManager, Intent intent) {
        super(appManager, intent);
        view = new ShowView(this);

        showly = (Showly) intent.getData(SHOWLY_INSTANCE_KEY);
        config = (ShowlyConfig) intent.getData(SHOWLY_CONFIG_KEY);
        convertSlidesToJavaFXSlides();
    }
    
    private void convertSlidesToJavaFXSlides() {
        var slides = (List<Slide>) intent.getData(SLIDE_LIST_KEY);
        slideList = new LinkedList<FxSlide>();
        
        for (var slide : slides) {
            slideList.add(new FxSlide(slide));
        }
    }

    @Override
    public AbstractView getView() {
        return view;
    }

    @Override
    public void onPresenterShown() {
        showFirstSlide();
        setIPAddress();
    }

    private void showFirstSlide() {
        view.setPresentationName(config.getPresentationName());
        var firstSlide = slideList.get(0);
        currentSlideIndex = 0;
        view.setSlideImage(firstSlide.getImage());
        view.setSlidePosition(slideList.size(), currentSlideIndex + 1);
    }

    private void setIPAddress() {
        var urls = getUrls();
        var message = "Tell others to try all these links on browser:";
        for(var aUrl: urls) {
            message += "\n" + aUrl;
        }
        view.setIpAddressMessage(message);
    }

    private List<String> getUrls() {
        List<String> addresses = new LinkedList<String>();
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();

            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();

                // Check if the interface is up and not a loopback or virtual interface
                if (networkInterface.isUp() && !networkInterface.isLoopback() && !networkInterface.isVirtual()) {
                    Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();

                    while (inetAddresses.hasMoreElements()) {
                        InetAddress inetAddress = inetAddresses.nextElement();

                        // Check if the address is an IPv4 address and not the loopback address
                        if (inetAddress.getHostAddress().matches("\\d+\\.\\d+\\.\\d+\\.\\d+") &&
                            !inetAddress.isLoopbackAddress()) {
                            addresses.add("http://" + inetAddress.getHostAddress() + ":" + config.getPort());
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return addresses;
    }

    public void nextSlide() {
        if(currentSlideIndex >= slideList.size() - 1) {
            return;
        }

        currentSlideIndex++;
        var nextSlide = slideList.get(currentSlideIndex);
        view.setSlideImage(nextSlide.getImage());
        view.setSlidePosition(slideList.size(), currentSlideIndex + 1);
    }

    public void previousSlide() {
        if(currentSlideIndex <= 0) {
            return;
        }

        currentSlideIndex--;
        var previousSlide = slideList.get(currentSlideIndex);
        view.setSlideImage(previousSlide.getImage());
        view.setSlidePosition(slideList.size(), currentSlideIndex + 1);
    }

    public void closePresenter() {
        showly.stop();
        closeCurrentPresenter();
    }
}
