package com.example.myapplication1710.electronic_horizon;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Road {
    //field-uri cu adnotari json
    @SerializedName("road_name")
    private String roadName;

    @SerializedName("segments")
    private List<Segment> segments;


    //segmentul in care ne aflam
    private Segment currentSegment;
    private int currentSeg_Id =-1;








/*--------------------------------------------------------------------------------------------------
                              Gasirea segmentului curent
--------------------------------------------------------------------------------------------------*/

    /** gaseste segmentul in care ne aflam in functie de latitudinea si longitudinea curenta */
    public int findCurrentSegment(double currentLatitude,double currentLongitude){

        int seg_id=-1;
        this.currentSeg_Id =-1;
        for(Segment segment:this.segments){
            if(segment.isInsideSegment(currentLatitude,currentLongitude)) {
                seg_id = segment.getIdSegment();
                this.currentSegment=segment; //salvez segment curent
                this.currentSeg_Id =seg_id;
                return seg_id;
            }
        }
        return seg_id;
    }


/*--------------------------------------------------------------------------------------------------
                              Limita de viteza curenta
--------------------------------------------------------------------------------------------------*/

    public int findCurrentSpeedLimit() {
        int currentSL = -1;

        if(this.currentSegment != null && this.currentSeg_Id != -1)
            currentSL=this.currentSegment.getSpeedLimit();

        return currentSL;
    }


/*--------------------------------------------------------------------------------------------------
                                  toString
--------------------------------------------------------------------------------------------------*/
    @Override
    public String toString() {
        return "Road{" +
                "roadName='" + roadName + '\'' +
                ", segments=" + segments +
                '}';
    }




/*--------------------------------------------------------------------------------------------------
                              Setter-e pentru uniteste
--------------------------------------------------------------------------------------------------*/
    //settere le folosesc pentru uniteste
    public void setRoadName(String roadName) {
    this.roadName = roadName;
}

    public void setSegments(List<Segment> segments) {
        this.segments = segments;
    }

}
