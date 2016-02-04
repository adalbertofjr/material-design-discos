package com.adalbertofjr.discos.dao;


import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.RenamingDelegatingContext;

import com.adalbertofjr.discos.data.DiscoDbHelper;
import com.adalbertofjr.discos.dominio.Disco;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by AdalbertoF on 04/02/2016.
 */

@RunWith(AndroidJUnit4.class)
public class DiscoDAOTest {
    Context mMockContext;

    @Before
    public void setup() {
        mMockContext = new RenamingDelegatingContext(
                InstrumentationRegistry.getInstrumentation().getTargetContext(),
                "test_"
        );
        mMockContext.deleteDatabase(DiscoDbHelper.DATABASE_NAME);
    }

    @Test
    public void testInserirLivro(){
        Disco disco = new Disco();
        disco.titulo= "Disco Teste";
        disco.ano = 2015;
        disco.gravadora= "BMG";
        disco.formacao =new String[]{"Eu", "tu"};
        disco.faixas= new String[]{"Faixa1","Faixa2"};

        DiscoDAO discoDAO = new DiscoDAO(mMockContext);
        discoDAO.inserir(disco);

        assertThat(discoDAO.favorito(disco), is(true));

        discoDAO.excluir(disco);
        assertThat(discoDAO.favorito(disco), is(false));

    }
}
