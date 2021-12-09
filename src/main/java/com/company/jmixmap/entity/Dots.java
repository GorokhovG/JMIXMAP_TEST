package com.company.jmixmap.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.JmixProperty;
import io.jmix.core.metamodel.annotation.PropertyDatatype;
import io.jmix.maps.Geometry;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.impl.PackedCoordinateSequenceFactory;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@JmixEntity
@Table(name = "GG_DOTS_ENTITY", indexes = {
        @Index(name = "IDX_DOTSENTITY_ICON_ID", columnList = "ICON_ID")
})
@Entity(name = "gg_DotsEntity")
public class Dots {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @InstanceName
    @NotNull
    @Column(name = "CODE", nullable = false)
    private Integer code;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "LATITUDE")
    private Double latitude;

    @Column(name = "LONGITUDE")
    private Double longitude;

    @JoinColumn(name = "ICON_ID")
    @OneToOne(fetch = FetchType.LAZY, optional = true)
    private Icons icon;

    @Geometry
    @PropertyDatatype("geoPoint")
    @Column(name = "DOT")
    @JmixProperty
    private Point dot;

    public Point getDot() {
        return dot;
    }

    public void setDot(double x, double y) {
        PackedCoordinateSequenceFactory sf = new PackedCoordinateSequenceFactory();
        GeometryFactory gf = new GeometryFactory();
        this.dot = gf.createPoint(sf.create(new double[] { x, y }, 2));
    }

    public void setDot(Point dot) {
        this.dot = dot;
    }

    public Icons getIcon() {
        return icon;
    }

    public void setIcon(Icons icon) {
        this.icon = icon;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}