package com.company.jmixmap.screen.dots;

import io.jmix.mapsui.component.GeoMap;
import io.jmix.mapsui.component.layer.VectorLayer;
import io.jmix.mapsui.component.layer.style.GeometryStyle;
import io.jmix.mapsui.component.layer.style.GeometryStyles;
import io.jmix.mapsui.component.layer.style.PointStyle;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.*;
import com.company.jmixmap.entity.Dots;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

@UiController("gg_DotsEntity.browse")
@UiDescriptor("dots-browse.xml")
@LookupComponent("dotsesTable")
public class DotsBrowse extends StandardLookup<Dots> {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(DotsBrowse.class);
    @Autowired
    private GeoMap map;

    @Autowired
    private InstanceContainer<Dots> dotsesDc;

    @Autowired
    private GeometryStyles geometryStyles;

    @Autowired
    private GroupTable<Dots> dotsesTable;

    private ArrayList<Dots> dotsCollection = new ArrayList();

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
    }

    @Install(to = "map.dotsesLayer", subject = "styleProvider")
    private GeometryStyle mapOrderLayerStyleProvider(Dots dots) throws MalformedURLException {
        Random rnd = new Random();
        int id = 1 + rnd.nextInt(11);
        String charOne = id > 9? "0" : "";

        String image = "https://gorokhovg.github.io/icons/" + charOne + id + ".svg";
        URL url = new URL(image);

        return geometryStyles.point().withImageIcon(url).setIconSize(50, 50);
                //.withDivIcon("<div style=\"margin-left:-20px;margin-top:-35px;\"><img src=\""+ image +"\" width=50 height=50/></div>");
    }

    @Subscribe("map.dotsesLayer")
    public void onMapOrderLayerGeoObjectSelected(VectorLayer.GeoObjectSelectedEvent<Dots> event) {
        dotsCollection.add(event.getItem());
        dotsesTable.setSelected(dotsCollection);

        //log.info(event.getItem().getAddress());
    }

    @Install(to = "map.dotsesLayer", subject = "popupContentProvider")
    private String mapOrderLayerPopupContentProvider(Dots dots) {
        return String.format(
                "<b>Address: </b> %s " +
                        "<p>" +
                        "<b>Code: </b> %s",
                dots.getAddress(),
                dots.getCode());
    }
}