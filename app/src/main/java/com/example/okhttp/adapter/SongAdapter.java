package com.example.okhttp.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.okhttp.R;
import com.example.okhttp.actions.ISongUpdate;
import com.example.okhttp.data.response.Movie;
import com.example.okhttp.models.Song;
import com.squareup.picasso.Picasso;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.okhttp.utils.Config.*;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder>{

    public Context mContext;
    public List<Movie> mSongList;
    public ISongUpdate iSongUpdate;
    public RecyclerViewListen listen;

    public SongAdapter(Context mContext, List<Movie> mSongList, ISongUpdate iSongUpdate,RecyclerViewListen listen) {
        this.mContext = mContext;
        this.mSongList = mSongList;
        this.iSongUpdate = iSongUpdate;
        this.listen = listen;

    }



    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song,parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        Movie mMovie = mSongList.get(position);
        holder.tvId.setText(mMovie.date + "");
        holder.tvName.setText(mMovie.title);
//        holder.tvCreate.setText(mMovie.createdAt);
        Picasso.with(mContext).load(BASE_BACKDROP_PATH + mMovie.backdropPath).into(holder.img);
        holder.btnWatch.setOnClickListener(view -> {
            new DeleteTask().execute(Integer.valueOf(mMovie.date));
        });

    }

    @Override
    public int getItemCount() {
        return mSongList.size();
    }
    public interface RecyclerViewListen{
        void onClick(View v, int position);
    }

    public class SongViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvId, tvName;
        public ImageView img;
        public Button btnWatch;

        public SongViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
            tvId = itemView.findViewById(R.id.tv_id);
            img = itemView.findViewById(R.id.img_thumbnail);
            btnWatch = itemView.findViewById(R.id.btn_watch);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listen.onClick(view,getAdapterPosition());
        }
    }

    class DeleteTask extends AsyncTask<Integer, Void, String> {

        @Override
        protected String doInBackground(Integer... params) {
            int id =params[0];
            OkHttpClient client = new OkHttpClient();
            try {
                Request.Builder builder = new Request.Builder();
                Request request = new Request.Builder().url(BASE_URL_API + id).delete().build();
                Response response = client.newCall(request).execute();
                return response.body().toString();

            }catch (Exception e) {
                Log.e("Delete Song", e.toString());
            }

            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            iSongUpdate.update();
        }
    }
}
