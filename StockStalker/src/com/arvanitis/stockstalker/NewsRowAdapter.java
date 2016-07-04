package com.arvanitis.stockstalker; 
  
import java.util.List; 
  
import android.app.Activity; 
import android.content.Context; 
import android.graphics.Color; 
import android.text.Html; 
import android.view.LayoutInflater; 
import android.view.View; 
import android.view.ViewGroup; 
import android.widget.ArrayAdapter; 
import android.widget.ImageView; 
import android.widget.TextView; 
  
public class NewsRowAdapter extends ArrayAdapter<Item> { 
  
    private Activity activity; 
    private List<Item> items; 
    private Item objBean; 
    private int row; 
    //private DisplayImageOptions options; 
    //ImageLoader imageLoader; 
    String id; 
  
    public NewsRowAdapter(Activity act, int resource, List<Item> arrayList,String id) { 
        super(act, resource, arrayList); 
        this.activity = act; 
        this.row = resource; 
        this.items = arrayList; 
        this.id=id; 
  
        /*options = new DisplayImageOptions.Builder() 
                .showStubImage(R.drawable.profile) 
                .showImageForEmptyUri(R.drawable.profile).showImageOnFail(R.drawable.profile).cacheInMemory() 
                .cacheOnDisc()/*.displayer(new RoundedBitmapDisplayer(20)).build(); 
        imageLoader = ImageLoader.getInstance(); 
        imageLoader.init(ImageLoaderConfiguration 
                .createDefault(activity)); 
*/
    } 
  
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) { 
        View view = convertView; 
        ViewHolder holder; 
        if (view == null) { 
            LayoutInflater inflater = (LayoutInflater) activity 
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
            view = inflater.inflate(row, null); 
  
            holder = new ViewHolder(); 
            view.setTag(holder); 
        } else { 
            holder = (ViewHolder) view.getTag(); 
        } 
  
        if ((items == null) || ((position + 1) > items.size())) 
            return view; 
  
        objBean = items.get(position); 
  
        holder.tvTitle = (TextView) view.findViewById(R.id.title); 
        holder.tvDesc = (TextView) view.findViewById(R.id.detail); 
        holder.tvDate = (TextView) view.findViewById(R.id.address); 
        //holder.imgView = (ImageView) view.findViewById(R.id.img); 
        //holder.pbar = (ProgressBar) view.findViewById(R.id.pbar); 
        holder.tvTitle.setTextColor(Color.BLACK); 
        holder.tvDesc.setTextColor(Color.BLACK); 
        holder.tvDate.setTextColor(Color.BLACK); 
  
        //if(Html.fromHtml(objBean.getPubdate()).toString().equals(id)){ 
        if (holder.tvTitle != null && null != objBean.getTitle() 
                && objBean.getTitle().trim().length() > 0) { 
            holder.tvTitle.setText(Html.fromHtml(objBean.getTitle())); 
        } 
        if (holder.tvDesc != null && null != objBean.getDesc() 
                && objBean.getDesc().trim().length() > 0) { 
            holder.tvDesc.setText(Html.fromHtml(objBean.getDesc())); 
        } 
        if (holder.tvDate != null && null != objBean.getAddress() 
                && objBean.getAddress().trim().length() > 0) { 
            holder.tvDate.setText(Html.fromHtml(objBean.getAddress())); 
        } 
/*      if (holder.imgView != null) { 
            if (null != objBean.getLink() 
                    && objBean.getLink().trim().length() > 0) { 
                //final ProgressBar pbar = holder.pbar; 
  
                //imageLoader.init(ImageLoaderConfiguration 
                //      .createDefault(activity)); 
                //imageLoader.displayImage(objBean.getLink(), holder.imgView, 
                    //  options); 
  
            } else { 
                holder.imgView.setImageResource(R.drawable.ic_launcher); 
            } 
        }*/
        //} 
        return view; 
    } 
  
    public class ViewHolder { 
  
        public TextView tvTitle, tvDesc, tvDate; 
        private ImageView imgView; 
        //private ProgressBar pbar; 
  
    } 
      
} 