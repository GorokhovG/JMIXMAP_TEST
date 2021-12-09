package com.company.jmixmap.screen.dots;

import com.company.jmixmap.entity.Icons;
import io.jmix.core.DataManager;
import io.jmix.mapsui.component.CanvasLayer;
import io.jmix.mapsui.component.GeoMap;
import io.jmix.mapsui.component.PopupWindow;
import io.jmix.ui.component.Button;
import io.jmix.ui.screen.*;
import com.company.jmixmap.entity.Dots;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.persistence.internal.jpa.EntityManagerFactoryImpl;
import org.eclipse.persistence.internal.jpa.EntityManagerImpl;
import org.locationtech.jts.geom.Point;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;


@UiController("gg_DotsEntity.edit")
@UiDescriptor("dots-edit.xml")
@EditedEntityContainer("dotsDc")
public class DotsEdit extends StandardEditor<Dots> {

    @Autowired
    protected DataManager dataManager;
    @PersistenceContext
    private EntityManager entityManager;// = new EntityManagerImpl("insert_session");

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(DotsEdit.class);
    final static private int CODE = 0;
    final static private int ADDRESS = 1;
    final static private int LATITUDE = 2;
    final static private int LONGITUDE = 3;
    final static private int ICON = 4;

    @Install(to = "map.dotsLayer", subject = "tooltipContentProvider")
    private String mapDotsLayerTooltipContentProvider(Dots dots) {
        return dots.getAddress();
    }

    @Subscribe("importBtn")
    public void onImportBtnClick(Button.ClickEvent event) throws IOException, InvalidFormatException {
        File file = new File("C:\\Users\\georg\\IdeaProjects\\JMIXMAP\\icons\\list.xlsx");
        FileInputStream fis = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);

        Map<Integer, List<String>> data = new HashMap<>();
        int i = 0;
        for (Row row : sheet) {
            data.put(i, new ArrayList<String>());
            for (Cell cell : row) {
                switch (cell.getCellType()) {
                    case STRING:
                        data.get(i).add(cell.getRichStringCellValue().getString());
                        break;
                    case NUMERIC:
                        if (DateUtil.isCellDateFormatted(cell)) {
                            data.get(i).add(cell.getDateCellValue() + "");
                        } else {
                            data.get(i).add(cell.getNumericCellValue() + "");
                        }
                        break;
                    case BOOLEAN:
                        data.get(i).add(cell.getBooleanCellValue() + "");
                        break;
                    case FORMULA:
                        data.get(i).add(cell.getCellFormula() + "");
                        break;
                    default: data.get(i).add(" ");
                }
            }
            i++;
        }

        saveDataToDataBase(data);
    }

    private void saveDataToDataBase(Map<Integer, List<String>> data) {
        for (int j = 1; j < data.size(); j++) {
            int code = (int)Double.parseDouble(data.get(j).get(CODE));
            String address = data.get(j).get(ADDRESS);
            double latitude = Double.parseDouble(data.get(j).get(LATITUDE));
            double longitude = Double.parseDouble(data.get(j).get(LONGITUDE));
            int icon = (int)Double.parseDouble(data.get(j).get(ICON));

            log.info(code + " | " + address + " | " + latitude + " | " + longitude + " | " + icon);

            if (code == 0 || address.isEmpty() || latitude == 0 || longitude == 0)
                continue;

            Dots dots = dataManager.create(Dots.class);
            dots.setCode(code);
            dots.setAddress(address);
            dots.setLatitude(latitude);
            dots.setLongitude(longitude);
            dots.setIcon(null);
            dots.setDot(longitude, latitude);

            dataManager.save(dots);
        }
    }

    @Install(to = "map.dotsLayer", subject = "popupContentProvider")
    private String mapOrderLayerPopupContentProvider(Dots dots) {
        return String.format(
                "<b>Address: </b> %s " +
                        "<p>" +
                        "<b>Code: </b> %s",
                dots.getAddress(),
                dots.getCode());
    }
}