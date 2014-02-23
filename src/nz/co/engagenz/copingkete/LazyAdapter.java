package nz.co.engagenz.copingkete;

import java.util.ArrayList;
import java.util.HashMap;


import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

public class LazyAdapter extends BaseAdapter {
    
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; //用来下载图片的类，后面有介绍
    
    public LazyAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        data=d;
        inflater = (LayoutInflater)a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(a.getApplicationContext());
    }
    
    public LazyAdapter(LayoutInflater inflater ,Context context, ArrayList<HashMap<String, String>> d) {
        data=d;
        this.inflater = inflater;
        imageLoader=new ImageLoader(context);
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_row, null);

        TextView title = (TextView)vi.findViewById(R.id.title); 
//        TextView creator = (TextView)vi.findViewById(R.id.creator); 
        TextView date = (TextView)vi.findViewById(R.id.date); 
//        ImageView thumb_image = (ImageView)vi.findViewById(R.id.list_image); 
        
        HashMap<String, String> song = new HashMap<String, String>();
        song = data.get(position);
        
        // 设置ListView的相关�?
        title.setText(song.get(MainActivity.KEY_TITLE));
//        creator.setText(song.get(MainActivity.KEY_CREATOR));
        String dateStr = song.get(MainActivity.KEY_DATE);
        dateStr = dateStr.substring(0, dateStr.indexOf('+')-4);//keep to the minutes only
        date.setText(dateStr);
//        date.setText(.substring(0, end));
//        String url = song.get(MainActivity.KEY_THUMB_URL);
//        imageLoader.DisplayImage(url, thumb_image);
		
        return vi;
    }

	public void refresh(ArrayList<HashMap<String, String>> data){
		this.data = data;
		this.notifyDataSetChanged();
	}


    
     
}
