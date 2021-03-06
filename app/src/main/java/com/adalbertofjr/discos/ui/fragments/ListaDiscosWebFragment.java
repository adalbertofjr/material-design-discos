package com.adalbertofjr.discos.ui.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
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
import com.adalbertofjr.discos.dominio.Disco;
import com.adalbertofjr.discos.http.DiscoHttp;
import com.adalbertofjr.discos.singleton.DiscoApp;
import com.adalbertofjr.discos.ui.DetalheActivity;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by AdalbertoF on 30/01/2016.
 */
public class ListaDiscosWebFragment extends Fragment implements DiscoAdapter.AoClicarNoDiscoListener {
    @Bind(R.id.swipeRefresh)
    SwipeRefreshLayout mSwipe;
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    Disco[] mDiscos;
    DiscosDownloadTask mTask;

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

        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mTask = new DiscosDownloadTask();
                mTask.execute();
            }
        });
        mRecyclerView.setTag("web");
        mRecyclerView.setHasFixedSize(true);
        if (getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_PORTRAIT) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        }

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mDiscos == null) {
            if (mTask == null) {
                mTask = new DiscosDownloadTask();
                mTask.execute();
            } else if (mTask.getStatus() == AsyncTask.Status.RUNNING) {
                exibirProgresso();
            }
        } else {
            atualizarLista();
        }
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

    private void atualizarLista() {
        DiscoAdapter adapter = new DiscoAdapter(getActivity(), mDiscos);
        adapter.setAoClicarNoDiscoListener(this);
        mRecyclerView.setAdapter(adapter);
    }

    private void exibirProgresso() {
        mSwipe.post(new Runnable() {
            @Override
            public void run() {
                mSwipe.setRefreshing(true);
            }
        });
    }

    private class DiscosDownloadTask extends AsyncTask<Void, Void, Disco[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            exibirProgresso();
        }

        @Override
        protected Disco[] doInBackground(Void... params) {
            return DiscoHttp.obterDiscosDoServidor();
        }

        @Override
        protected void onPostExecute(Disco[] discos) {
            super.onPostExecute(discos);
            mSwipe.setRefreshing(false);
            if (discos != null) {
                mDiscos = discos;
                atualizarLista();
            }
        }
    }
}
