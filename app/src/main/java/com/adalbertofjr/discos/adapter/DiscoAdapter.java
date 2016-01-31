package com.adalbertofjr.discos.adapter;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.adalbertofjr.discos.R;
import com.adalbertofjr.discos.dominio.Disco;
import com.adalbertofjr.discos.http.DiscoHttp;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by AdalbertoF on 30/01/2016.
 */
public class DiscoAdapter extends RecyclerView.Adapter<DiscoAdapter.DiscoViewHolder> {
    private Context mContext;
    private Disco[] mDiscos;
    private AoClicarNoDiscoListener mListener;

    public DiscoAdapter(Context mContext, Disco[] mDiscos) {
        this.mContext = mContext;
        this.mDiscos = mDiscos;
    }

    public void setAoClicarNoDiscoListener(AoClicarNoDiscoListener l) {
        mListener = l;
    }

    @Override
    public DiscoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_disco, parent, false);
        DiscoViewHolder vh = new DiscoViewHolder(v);
        v.setTag(vh);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    DiscoViewHolder vh = (DiscoViewHolder) view.getTag();
                    int position = vh.getAdapterPosition();
                    mListener.aoClicarNoDisco(view, position, mDiscos[position]);
                }
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(DiscoViewHolder holder, int position) {
        Disco disco = mDiscos[position];
        Picasso.with(mContext).load(DiscoHttp.BASE_URL + disco.capa).into(holder.imgCapa);
        holder.txtTitulo.setText(disco.titulo);
        holder.txtAno.setText(String.valueOf(disco.ano));
    }

    @Override
    public int getItemCount() {
        return mDiscos != null ? mDiscos.length : 0;
    }

    public class DiscoViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.imgCapa)
        public ImageView imgCapa;
        @Bind(R.id.txtAno)
        public TextView txtTitulo;
        @Bind(R.id.txtTitulo)
        public TextView txtAno;

        public DiscoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            ViewCompat.setTransitionName(imgCapa, "capa");
            ViewCompat.setTransitionName(txtTitulo, "titulo");
            ViewCompat.setTransitionName(txtAno, "ano");
        }
    }

    public interface AoClicarNoDiscoListener {
        void aoClicarNoDisco(View view, int position, Disco disco);
    }
}
