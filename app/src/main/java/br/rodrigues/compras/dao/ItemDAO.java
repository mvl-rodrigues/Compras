package br.rodrigues.compras.dao;

import java.util.ArrayList;
import java.util.List;

import br.rodrigues.compras.model.Item;

public class ItemDAO {


    public List<Item> pegaItens() {
        List<Item> itens = new ArrayList<>();

        Item i = new Item();
        i.setNome("Victor");
        Item i2 = new Item();
        i2.setNome("Caio");
        Item i3 = new Item();
        i3.setNome("Pedro");
        itens.add(i);
        itens.add(i2);
        itens.add(i3);

        return itens;
    }
}
