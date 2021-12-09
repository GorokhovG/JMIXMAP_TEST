package com.company.jmixmap.screen.dots;

import io.jmix.mapsui.component.GeoMap;
import io.jmix.mapsui.component.layer.VectorLayer;
import io.jmix.mapsui.component.layer.style.GeometryStyles;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.*;
import com.company.jmixmap.entity.Dots;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

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
       // dotsesTable.setMultiSelect(true);

        VectorLayer<Dots> dotsLayer = new VectorLayer<>("dotsLayer", dotsesDc);
        dotsLayer.setEditable(true);
        map.addLayer(dotsLayer);
        map.selectLayer(dotsLayer);
    }

    @Subscribe("map.dotsesLayer")
    public void onMapOrderLayerGeoObjectSelected(VectorLayer.GeoObjectSelectedEvent<Dots> event) {
        dotsCollection.add(event.getItem());
        //dotsesTable.;
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