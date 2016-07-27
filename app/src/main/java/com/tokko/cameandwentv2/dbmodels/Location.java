package com.tokko.cameandwentv2.dbmodels;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Andreas on 27/07/2016.
 */
@Table(name = "Locations")
public class Location extends Model {
    @Column(name = "Longitude")
    public long longitude;
    @Column(name = "Latitude")
    public long latitude;
    @Column(name = "Name")
    public String name;
}
