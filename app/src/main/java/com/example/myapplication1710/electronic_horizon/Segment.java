package com.example.myapplication1710.electronic_horizon;

import com.google.gson.annotations.SerializedName;

public class Segment  implements  Comparable<Segment> {

    @SerializedName("id_segment")
    private int idSegment;

    @SerializedName("geopoint_1")
    private GeoPoint geopoint1;
    @SerializedName("geopoint_2")
    private GeoPoint geopoint2;
    @SerializedName("geopoint_3")
    private GeoPoint geopoint3;
    @SerializedName("geopoint_4")
    private GeoPoint geopoint4;

    @SerializedName("speed_limit")
    private int speedLimit;

    //un field nou pt imagini ??




/*--------------------------------------------------------------------------------------------------
                              Constructor
--------------------------------------------------------------------------------------------------*/
    public Segment(int idSegment, GeoPoint geopoint1, GeoPoint geopoint2, GeoPoint geopoint3, GeoPoint geopoint4, int speedLimit) {
        this.idSegment = idSegment;
        this.geopoint1 = geopoint1;
        this.geopoint2 = geopoint2;
        this.geopoint3 = geopoint3;
        this.geopoint4 = geopoint4;
        this.speedLimit = speedLimit;
    }




/*--------------------------------------------------------------------------------------------------
                           Segmentul curent activ
--------------------------------------------------------------------------------------------------*/

    /** verifica daca GeoPoint-ul current apartine Segmentului*/
    public boolean isInsideSegment(double currentLatitude,double currentLongitude){
        return isInsideSegmentTesting(currentLatitude,currentLongitude);
    }


    //in testare
    /** orientarea celor 4 triunghiuri formate intre punctele segmentelor si locatia curenta*/
    public boolean isInsideSegmentTesting(double currentLatitude,double currentLongitude){
        GeoPoint currentPoint= new GeoPoint(currentLatitude,currentLongitude);

        //v2 cu TRIANGULARIZARE
        //primul triunghi : G1G2G3
        int singT1_1 =signSarrus(this.geopoint1,this.geopoint2,currentPoint);
        int singT1_2 =signSarrus(this.geopoint2,this.geopoint3,currentPoint);
        int singT1_3 =signSarrus(this.geopoint3,this.geopoint1,currentPoint);

        //al doilea triunghi : G1G3G4
        int singT2_1 =signSarrus(this.geopoint1,this.geopoint3,currentPoint);
        int singT2_2 =signSarrus(this.geopoint3,this.geopoint4,currentPoint);
        int singT2_3 =signSarrus(this.geopoint4,this.geopoint1,currentPoint);

        return (singT1_1 ==singT1_2 && singT1_2 == singT1_3) ||
                (singT2_1 ==singT2_2 && singT2_2 == singT2_3);

    }

    public int signSarrus(GeoPoint gp1,GeoPoint gp2,GeoPoint gp3){
        Double determinant =gp1.latitude *(gp2.longitude-gp3.longitude)+
                gp2.latitude*(gp3.longitude-gp1.longitude)+
                gp3.latitude*(gp1.longitude-gp2.longitude);
        if(determinant >0)
            return 1;
        else if(determinant<0)
            return -1;
        else return 0;
    }




/*--------------------------------------------------------------------------------------------------
                          Compare
--------------------------------------------------------------------------------------------------*/
    /** compararea segmentelor in functie de id*/
    @Override
    public int compareTo(Segment o) {
        return Integer.compare(this.idSegment, o.idSegment);
    }




/*--------------------------------------------------------------------------------------------------
                               Getter
--------------------------------------------------------------------------------------------------*/
    //getter
    public int getIdSegment() {
        return idSegment;
    }

    public int getSpeedLimit() {
        return speedLimit;
    }

/*--------------------------------------------------------------------------------------------------
                                   ToString
--------------------------------------------------------------------------------------------------*/
    @Override
    public String toString() {
        return "Segment{" +
                "idSegment=" + idSegment +
                ", geopoint1=" + geopoint1 +
                ", geopoint2=" + geopoint2 +
                ", geopoint3=" + geopoint3 +
                ", geopoint4=" + geopoint4 +
                ", speedLimit=" + speedLimit +
                '}'+'\n';
    }

}
