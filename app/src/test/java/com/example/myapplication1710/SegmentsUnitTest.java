package com.example.myapplication1710;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

import com.example.myapplication1710.electronic_horizon.GeoPoint;
import com.example.myapplication1710.electronic_horizon.Road;
import com.example.myapplication1710.electronic_horizon.Segment;
import com.example.myapplication1710.utils.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class SegmentsUnitTest {

    public Road initRoadData (){
        Segment s1 = new Segment(0, new GeoPoint(46.8234135, 23.7424555), new GeoPoint(46.8227519, 23.7466463), new GeoPoint(46.8366683, 23.7488431), new GeoPoint(46.8373299, 23.7446523), 50);
        Segment s2 = new Segment(1, new GeoPoint(46.8383412, 23.7449961), new GeoPoint(46.8360146, 23.7485439), new GeoPoint(46.8772111, 23.7755595), new GeoPoint(46.8795377, 23.7720117), 70);
        Segment s3 = new Segment(2, new GeoPoint(46.8795084, 23.7720157), new GeoPoint(46.8776538, 23.7758315), new GeoPoint(46.8891309, 23.78141), new GeoPoint(46.8909855, 23.7775942), 100);
        Segment s4 = new Segment(3, new GeoPoint(46.8895873, 23.7774659), new GeoPoint(46.8909263, 23.7814917), new GeoPoint(46.8953121, 23.7800329), new GeoPoint(46.8939731, 23.7760071), 100);
        Segment s5 = new Segment(4, new GeoPoint(46.8940979, 23.7759273), new GeoPoint(46.8955421, 23.7799165), new GeoPoint(46.9076566, 23.7755309), new GeoPoint(46.9062124, 23.7715417), 50);
        Segment s6 = new Segment(5, new GeoPoint(46.9081182, 23.7717063), new GeoPoint(46.906099, 23.7754377), new GeoPoint(46.926356, 23.7864001), new GeoPoint(46.9283752, 23.7826687), 50);
        Segment s7 = new Segment(6, new GeoPoint(46.9282968, 23.7826228), new GeoPoint(46.9267214, 23.7865622), new GeoPoint(46.9402884, 23.7919879), new GeoPoint(46.9418638, 23.7880485), 100);

        List<Segment> segments = new ArrayList<>();
        segments.add(s1);
        segments.add(s2);
        segments.add(s3);
        segments.add(s4);
        segments.add(s5);
        segments.add(s6);
        segments.add(s7);

        Road road=new Road();

        road.setRoadName("E576");
        road.setSegments(segments);
        return road;

    }


    @Test
    public void checkSegments() {
        Road road = initRoadData();


        //no segment found
        int seg_no_1=road.findCurrentSegment(46.8371779,  23.74677 );
        assertTrue(-1== seg_no_1 && -1 ==road.findCurrentSpeedLimit()  );

        int seg_no_2=road.findCurrentSegment(46.756536, 23.544942 );
        assertTrue(-1== seg_no_2 && -1 ==road.findCurrentSpeedLimit()  );



        //seg 0 ,limita de 50
        int seg_id0=road.findCurrentSegment(46.8232453,  23.7445489  );
        assertTrue(0== seg_id0 && 50 ==road.findCurrentSpeedLimit()  );

        int seg_id01=road.findCurrentSegment(46.8239369,  23.7446254 );
        assertTrue(0== seg_id01 && 50 ==road.findCurrentSpeedLimit()  );

        //inserez si una de exterior intre sa vad daca isi revine sistemul
        int seg_no_3=road.findCurrentSegment(46.756536, 23.544942 );
        assertTrue(-1== seg_no_3 && -1 ==road.findCurrentSpeedLimit() );

        int seg_id02=road.findCurrentSegment(46.8243154,  23.744684 );
        assertTrue(0== seg_id02 && 50 ==road.findCurrentSpeedLimit()  );

        int seg_id03=road.findCurrentSegment(46.8246997, 23.744745 );
        assertTrue(0== seg_id03 && 50 ==road.findCurrentSpeedLimit() );




        //seg 1,limita de 70
        int seg_id1=road.findCurrentSegment(46.837729, 23.746876);
        assertTrue(1== seg_id1 && 70 ==road.findCurrentSpeedLimit()  );

        int seg_id11=road.findCurrentSegment(46.8378858,  23.7470048  );
        assertTrue(1== seg_id11 && 70 ==road.findCurrentSpeedLimit()  );

        int seg_id12=road.findCurrentSegment(46.8380531, 23.7471047  );
        assertTrue(1== seg_id12 && 70 ==road.findCurrentSpeedLimit()  );

        int seg_no_4=road.findCurrentSegment(46.756536, 23.544942 );
        assertTrue(-1== seg_no_4 && -1 ==road.findCurrentSpeedLimit()  );

        int seg_id13=road.findCurrentSegment(46.8383862,  23.7473337  );
        assertTrue(1== seg_id13 && 70 ==road.findCurrentSpeedLimit()  );


        int seg_id14=road.findCurrentSegment(46.838886, 23.747684  );
        assertTrue(1== seg_id14 && 70 ==road.findCurrentSpeedLimit());

        int seg_id15=road.findCurrentSegment( 46.8390559,  23.7478023  );
        assertTrue(1== seg_id15 && 70 ==road.findCurrentSpeedLimit());

        int seg_no_5=road.findCurrentSegment(46.756536, 23.544942 );
        assertTrue(-1== seg_no_5 && -1 ==road.findCurrentSpeedLimit() );


        //seg2 , limita de 100
        int seg_id20=road.findCurrentSegment( 46.8790007,  23.7741993  );
        assertTrue(2== seg_id20 && 100 ==road.findCurrentSpeedLimit());

        int seg_id21=road.findCurrentSegment( 46.8792134,  23.7743366  );
        assertTrue(2== seg_id21 && 100 ==road.findCurrentSpeedLimit());

        int seg_id22=road.findCurrentSegment(  46.8796325,23.774602  );
        assertTrue(2== seg_id22 && 100 ==road.findCurrentSpeedLimit());

        int seg_no_6=road.findCurrentSegment(46.756536, 23.544942 );
        assertTrue(-1== seg_no_6 && -1 ==road.findCurrentSpeedLimit() );


        //seg3 ,limita de 100    46.8906544, 23.7793949
        int seg_id30=road.findCurrentSegment(  46.8906544, 23.7793949  );
        assertTrue(3== seg_id30 && 100 ==road.findCurrentSpeedLimit());


        int seg_id31=road.findCurrentSegment(  46.8910565,23.7793151  );
        assertTrue(3== seg_id31 && 100 ==road.findCurrentSpeedLimit());

        int seg_no_7=road.findCurrentSegment(46.756536, 23.544942 );
        assertTrue(-1== seg_no_7 && -1 ==road.findCurrentSpeedLimit() );

        int seg_id32=road.findCurrentSegment(  46.8914684, 23.7792399  );
        assertTrue(3== seg_id32 && 100 ==road.findCurrentSpeedLimit());


        //seg4 ,limita de 50
        int seg_id40=road.findCurrentSegment(  46.8955203,  23.7775331  );
        assertTrue(4== seg_id40 && 50 ==road.findCurrentSpeedLimit());

        int seg_no_8=road.findCurrentSegment(46.756536, 23.544942 );
        assertTrue(-1== seg_no_8 && -1 ==road.findCurrentSpeedLimit() );

        //seg 5 ,limita de 50
        int seg_id50=road.findCurrentSegment(  46.9079016, 23.7741487 );
        assertTrue(5== seg_id50 && 50 ==road.findCurrentSpeedLimit());

        int seg_no_9=road.findCurrentSegment(46.756536, 23.544942 );
        assertTrue(-1== seg_no_9 && -1 ==road.findCurrentSpeedLimit() );

        int seg_id51=road.findCurrentSegment(  46.9082103, 23.7744165 );
        assertTrue(5== seg_id51 && 50 ==road.findCurrentSpeedLimit());
         //46.9088474,  23.7749635
        //46.9090128,  23.7750879
        int seg_id52=road.findCurrentSegment(  46.9088474,  23.7749635 );
        assertTrue(5== seg_id52 && 50 ==road.findCurrentSpeedLimit());

        //seg 6 , limita de 100
        int seg_id60=road.findCurrentSegment(  46.9277928, 23.7846978);
        assertTrue(6== seg_id60 && 100 ==road.findCurrentSpeedLimit());

    }


    @Test
    public void trebe_sters(){
        double speed_d = 30.19899;
        int speed_i=(int)speed_d;
        assertEquals(30, speed_i);
    }


}