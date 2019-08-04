package com.example.contactswhatsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    public static final int VIEW_TYPE_MESSAGE = 0;
    public static final int VIEW_TYPE_IMAGE = 1;
    public static final int VIEW_TYPE_DOCUMENT = 3;
    public static final int VIEW_AUTO_MESSAGE =2 ;


    Context context;
    List<Data> dataList;

    public CustomAdapter(Context context, List<Data> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view ;
        Log.e("ViewType", String.valueOf(viewType));
        switch (viewType) {
            case VIEW_TYPE_MESSAGE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_messages, parent, false);
                return new MessageViewHolder(view);
            case VIEW_TYPE_IMAGE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_images, parent, false);
                return new ImagesViewHolder(view);
            case VIEW_TYPE_DOCUMENT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_document, parent, false);
                return  new DocumentViewHolder(view);
            case VIEW_AUTO_MESSAGE:
                view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_messages_auto,parent,false);
                return  new AutoMessageViewHolder(view);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder customViewHolder, int position) {
        Log.e("getItemViewType", String.valueOf(getItemViewType(position)));
        try {
            switch (getItemViewType(position)) {
                case VIEW_TYPE_MESSAGE:
                    MessageViewHolder messageViewHolder = (MessageViewHolder) customViewHolder;
                    messageViewHolder.bindMessage(dataList.get(position));
                    break;
                case VIEW_TYPE_IMAGE:
                    ImagesViewHolder imagesViewHolder= (ImagesViewHolder) customViewHolder;
                    imagesViewHolder.bindImage(dataList.get(position));
                    break;
                case VIEW_TYPE_DOCUMENT:
                    DocumentViewHolder documentViewHolder= (DocumentViewHolder) customViewHolder;
                    documentViewHolder.bindDocument(dataList.get(position));
                    break;
                case VIEW_AUTO_MESSAGE:
                    AutoMessageViewHolder autoMessageViewHolder= (AutoMessageViewHolder) customViewHolder;
                    autoMessageViewHolder.bindAutoMessage(dataList.get(position));
                    break;
                default:
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        // return  dataList.get(position).getData_type();
        switch (dataList.get(position).getData_type()) {
            case 0:
                return VIEW_TYPE_MESSAGE;
            case 1:
                return VIEW_TYPE_IMAGE;
            case 2:
                return VIEW_AUTO_MESSAGE;
            case 3:
                return VIEW_TYPE_DOCUMENT;
            default:
                return -1;
        }
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class MessageViewHolder extends CustomViewHolder {
        TextView textMessage ,textLastSpokeTime;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            textMessage = itemView.findViewById(R.id.message);
            textLastSpokeTime = itemView.findViewById(R.id.lastSpokeTime);
        }

        public void bindMessage(Data data) {
            textMessage.setText(data.getData().toString());
            textLastSpokeTime.setText(data.getSendDataTime().toString());
            Log.e("data.getData", data.getData().toString());

        }
    }


    public class ImagesViewHolder extends CustomViewHolder {
        ImageView imageView;
        TextView txtLastPhotedTime;
        public ImagesViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.photo);
            txtLastPhotedTime=itemView.findViewById(R.id.lastPhotoTime);
        }
        public void bindImage(Data data){
            Picasso.get().load(data.getData().trim()).into(imageView);
            txtLastPhotedTime.setText(data.getSendDataTime());
        }
    }


    public class DocumentViewHolder extends CustomViewHolder {
        ImageView imageView;
        TextView txtFileName,txtFileType,txtFileSendTime;

        public DocumentViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.icon);
            txtFileName=itemView.findViewById(R.id.nameFile);
            txtFileType=itemView.findViewById(R.id.typeFile);
            txtFileSendTime=itemView.findViewById(R.id.sendTime);

        }
        public void bindDocument(Data data){
            imageView.setImageResource(R.drawable.icon_file_red_24dp);
            txtFileType.setText(data.getData().substring(data.getData().lastIndexOf(".")));
            if(data.getData().length()>22){
                txtFileName.setText(data.getData().substring(0,22).concat("..."));

            } else{
                txtFileName.setText(data.getData());
            }
            txtFileSendTime.setText(data.getSendDataTime());
            Log.e("time",data.getSendDataTime());
            Log.e("fileType", data.getData().substring(data.getData().lastIndexOf(".")));



        }
    }

    public class AutoMessageViewHolder extends CustomViewHolder {
        TextView textMessage ,textLastSpokeTime;

        public AutoMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            textMessage = itemView.findViewById(R.id.message);
            textLastSpokeTime = itemView.findViewById(R.id.lastSpokeTime);
        }

        public void bindAutoMessage(Data data) {
            textMessage.setText(data.getData().toString());
            textLastSpokeTime.setText(data.getSendDataTime().toString());
            Log.e("data.getData", data.getData().toString());

        }
    }



//    public void splitTextView(String text,TextView textView){
//        int index=0;
//        StringBuffer finalString=new StringBuffer();
//        while (index < text.length()) {
//            Log.i( "test = " , text.substring(index, Math.min(index + 20,text.length())));
//            finalString.append(text.substring(index, Math.min(index + 20,text.length()))+"\n");
//            index += 20;
//        }
//        Log.i( "finalString = " , String.valueOf(finalString));
//
//        textView.setText(finalString);
//    }


}
