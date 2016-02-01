package com.adalbertofjr.discos.ui.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adalbertofjr.discos.R;
import com.adalbertofjr.discos.adapter.DiscoAdapter;
import com.adalbertofjr.discos.dao.DiscoDAO;
import com.adalbertofjr.discos.dominio.Disco;
import com.adalbertofjr.discos.ui.DetalheActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by AdalbertoF on 01/02/2016.
 */
public class ListaDiscosFavoritosFragment extends Fragment implements DiscoAdapter.AoClicarNoDiscoListener {
    @Bind(R.id.swipeRefresh)
    SwipeRefreshLayout mSwipe;
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    DiscoDAO mDiscoDAO;
    List<Disco> mDiscos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.lista_discos, container, false);
        ButterKnife.bind(this, v);
        mSwipe.setEnabled(false);
        mRecyclerView.setTag("fav");
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        if (getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_PORTRAIT) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        }
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDiscoDAO = new DiscoDAO(getActivity());
        if (mDiscos == null) {
            mDiscos = mDiscoDAO.getDiscos();
        }

        atualizarLista();
    }

    private void atualizarLista() {
        Disco[] array = new Disco[mDiscos.size()];
        mDiscos.toArray(array);
        DiscoAdapter adapter = new DiscoAdapter(getActivity(), array);
        adapter.setAoClicarNoDiscoListener(this);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void aoClicarNoDisco(View view, int position, Disco disco) {
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                        Pair.create(view.findViewById(R.id.imgCapa), "capa"),
                        Pair.create(view.findViewById(R.id.txtTitulo), "titulo"),
                        Pair.create(view.findViewById(R.id.txtAno), "ano")
                );
        Intent it = new Intent(getActivity(), DetalheActivity.class);
        it.putExtra(DetalheActivity.EXTRA_DISCO, disco);
        ActivityCompat.startActivity(getActivity(), it, options.toBundle());
    }
}
