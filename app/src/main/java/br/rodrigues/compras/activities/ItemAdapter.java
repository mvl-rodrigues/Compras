package br.rodrigues.compras.activities;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import br.rodrigues.compras.R;
import br.rodrigues.compras.model.Item;

public class ItemAdapter extends BaseAdapter {

    private Context context;
    private List<Item> items;

    public ItemAdapter (Context context, List<Item> items){
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View viewCriada = criaViews(viewGroup);
        Item item = items.get(position);
        vincula(viewCriada, item);

        return viewCriada;
    }

    private void vincula(View view, Item item) {

        TextView nome = view.findViewById(R.id.item_campo_nome);
        TextView preco = view.findViewById(R.id.item_campo_preco);
        TextView precoSimbulo = view.findViewById(R.id.tv_preco_simbulo);
        Spinner categorias = view.findViewById(R.id.item_campo_categorias);
        TextView categoria = view.findViewById(R.id.item_campo_categoria);
        ImageView comprado = view.findViewById(R.id.item_image_button_comprado);

        nome.setText(item.getNome());
        preco.setText(new ListaItensHelper().formatarEmReais(item.getPreco()));
        categoria.setText(categorias.getItemAtPosition(item.getCategoria()).toString());



        if (item.getComprado()){
            comprado.setVisibility(View.VISIBLE);

            nome.setTextColor(Color.GRAY);
            nome.setPaintFlags(nome.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            preco.setTextColor(Color.GRAY);
            preco.setPaintFlags(nome.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            precoSimbulo.setTextColor(Color.GRAY);
            precoSimbulo.setPaintFlags(nome.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            categoria.setTextColor(Color.GRAY);
            categoria.setPaintFlags(nome.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

    }

    private View criaViews(ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.item, viewGroup, false);
    }

    public void update (List<Item> items){
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }
}
