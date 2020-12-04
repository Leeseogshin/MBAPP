package com.example.teamproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter{
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>() ;

    // ListViewAdapter의 생성자
    public ListViewAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.subactivity, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득

        TextView textflightid = (TextView) convertView.findViewById(R.id.textflightid) ;
        TextView textterminalid = (TextView) convertView.findViewById(R.id.textterminalid);
        TextView textairline = (TextView) convertView.findViewById(R.id.textairline);
        TextView textairport = (TextView) convertView.findViewById(R.id.textairport);
        TextView textairportCode = (TextView) convertView.findViewById(R.id.textairportCode);
        TextView textchkinrange = (TextView) convertView.findViewById(R.id.textchkinrange);
        TextView textestimatedDateTime = (TextView) convertView.findViewById(R.id.textestimatedDateTime);
        TextView textgatenumber = (TextView) convertView.findViewById(R.id.textgatenumber);
        TextView texthimidity = (TextView) convertView.findViewById(R.id.texthimidity);
        TextView textmaxterm = (TextView) convertView.findViewById(R.id.textmaxterm);
        TextView textminterm = (TextView) convertView.findViewById(R.id.textminterm);
        TextView textremark = (TextView) convertView.findViewById(R.id.textremark);
        TextView textscheduledDateTime = (TextView) convertView.findViewById(R.id.textscheduledDateTime);
        TextView textwind = (TextView) convertView.findViewById(R.id.textwind);
        TextView textyoil = (TextView) convertView.findViewById(R.id.textyoil);


        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ListViewItem listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        textflightid.setText(listViewItem.getFlightid());
        textterminalid.setText(listViewItem.getTerminalid() + "도");
        textairline.setText(listViewItem.getAirline());
        textairport.setText(listViewItem.getAirport());
        textairportCode.setText(listViewItem.getAirportCode());
        textchkinrange.setText(listViewItem.getChkinrange());
        textestimatedDateTime.setText(listViewItem.getEstimatedDateTime());
        textgatenumber.setText(listViewItem.getGatenumber());
        texthimidity.setText(listViewItem.getHimidity());
        textmaxterm.setText(listViewItem.getMaxterm());
        textminterm.setText(listViewItem.getMinterm());
        textremark.setText(listViewItem.getRemark() + "%");
        textscheduledDateTime.setText(listViewItem.getScheduledDateTime() + "도");
        textwind.setText(listViewItem.getWind() + "m/s");
        textyoil.setText(listViewItem.getYoil() + "요일");

        ImageView imgview = (ImageView) convertView.findViewById(R.id.imgView);

        String imageUrl = listViewItem.getWimage();
        String[] ar = imageUrl.split("/");
        String a = ar[7].trim();
        if(a.equals("icon1.png"))
            imgview.setImageResource(R.drawable.icon1);
        else if(a.equals("icon2.png")) imgview.setImageResource(R.drawable.icon2);
        else if(a.equals("icon3.png")) imgview.setImageResource(R.drawable.icon3);
        else if(a.equals("icon4.png")) imgview.setImageResource(R.drawable.icon4);
        else if(a.equals("icon5.png")) imgview.setImageResource(R.drawable.icon5);
        else if(a.equals("icon6.png")) imgview.setImageResource(R.drawable.icon6);
        else if(a.equals("icon7.png")) imgview.setImageResource(R.drawable.icon7);
        else if(a.equals("icon8.png")) imgview.setImageResource(R.drawable.icon8);
        else if(a.equals("icon9.png")) imgview.setImageResource(R.drawable.icon9);
        else if(a.equals("icon10.png")) imgview.setImageResource(R.drawable.icon10);
        else if(a.equals("icon11.png")) imgview.setImageResource(R.drawable.icon11);
        else if(a.equals("icon12.png")) imgview.setImageResource(R.drawable.icon12);
        else imgview.setImageResource(R.drawable.icon1);



        return convertView;
    }


    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }


    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem( String airline, String airport, String airportCode, String chkinrange,
                         String estimatedDateTime, String flightid, String gatenumber, String remark,
                         String scheduledDateTime, String terminalid) {
        ListViewItem item = new ListViewItem();


        item.setAirline(airline);
        item.setAirport(airport);
        item.setAirportCode(airportCode);
        item.setChkinrange(chkinrange);
        item.setScheduledDateTime(scheduledDateTime);
        item.setEstimatedDateTime(estimatedDateTime);
        item.setFlightid(flightid);
        item.setGatenumber(gatenumber);
        item.setRemark(remark);
        item.setTerminalid(terminalid);

        listViewItemList.add(item);
    }

    //오버로딩 구현
    public void addItem( String airline, String airport, String airportCode, String chkinrange,
                         String estimatedDateTime, String flightid, String gatenumber, String remark,
                         String scheduledDateTime, String terminalid,
                         String himidity, String maxterm, String minterm, String wimage, String wind, String yoil) {
        ListViewItem item = new ListViewItem();

        item.setMinterm(minterm);
        item.setWimage(wimage);
        item.setWind(wind);
        item.setYoil(yoil);
        item.setMaxterm(maxterm);
        item.setAirline(airline);
        item.setAirport(airport);
        item.setAirportCode(airportCode);
        item.setChkinrange(chkinrange);
        item.setScheduledDateTime(scheduledDateTime);
        item.setEstimatedDateTime(estimatedDateTime);
        item.setFlightid(flightid);
        item.setGatenumber(gatenumber);
        item.setRemark(remark);
        item.setTerminalid(terminalid);
        item.setHimidity(himidity);


        listViewItemList.add(item);
    }

}
