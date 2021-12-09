package com.company.jmixmap.screen.map;

import com.company.jmixmap.entity.Dots;
import io.jmix.mapsui.component.GeoMap;
import io.jmix.mapsui.component.layer.VectorLayer;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.*;
import com.company.jmixmap.entity.Map;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("gg_Map.browse")
@UiDescriptor("map-browse.xml")
@LookupComponent("mapsTable")
public class MapBrowse extends StandardLookup<Map> {

    @Autowired
    private GeoMap map;

    @Autowired
    private InstanceContainer<Dots> dotsesDc;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        // dotsesTable.setMultiSelect(true);

        VectorLayer<Dots> mapsLayer = new VectorLayer<>("mapsLayer", dotsesDc);
        mapsLayer.setEditable(true);
        map.addLayer(mapsLayer);
        map.selectLayer(mapsLayer);
    }
}