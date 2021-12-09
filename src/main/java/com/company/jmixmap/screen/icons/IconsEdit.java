package com.company.jmixmap.screen.icons;

import io.jmix.ui.component.Button;
import io.jmix.ui.download.DownloadFormat;
import io.jmix.ui.download.Downloader;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.*;
import com.company.jmixmap.entity.Icons;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("gg_IconEntity.edit")
@UiDescriptor("icons-edit.xml")
@EditedEntityContainer("iconsDc")
public class IconsEdit extends StandardEditor<Icons> {

    @Autowired
    private InstanceContainer<Icons> iconsDc;

    @Autowired
    private Downloader downloader;

    @Subscribe("downloadBtn")
    public void onDownloadBtnClick(Button.ClickEvent event) {
        byte[] photo = iconsDc.getItem().getIcon();
        downloader.download(
                photo,
                iconsDc.getItem().getId() + "-photo",
                DownloadFormat.PNG
        );
    }
}