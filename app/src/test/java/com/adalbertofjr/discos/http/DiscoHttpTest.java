package com.adalbertofjr.discos.http;

import android.test.suitebuilder.annotation.SmallTest;

import com.adalbertofjr.discos.dominio.Disco;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Created by AdalbertoF on 04/02/2016.
 */

@SmallTest
public class DiscoHttpTest {

    @Test
    public void testDownloadLivros(){
        Disco[] discos= DiscoHttp.obterDiscosDoServidor();
        assertThat(discos, notNullValue());
        assertThat(discos.length, is(not(lessThanOrEqualTo(0))));
    }
}
