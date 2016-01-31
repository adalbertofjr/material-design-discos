package com.adalbertofjr.discos.dominio;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by AdalbertoF on 30/01/2016.
 */
public class Disco implements Serializable {
    public String titulo;
    public String capa;
    @SerializedName("capa_big")
    public String capaGrande;
    public int ano;
    public String gravadora;
    public String[] formacao;
    public String[] faixas;
}
