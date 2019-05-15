package br.rodrigues.compras.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import br.rodrigues.compras.R;
import br.rodrigues.compras.dao.ItemDAO;
import br.rodrigues.compras.model.Item;

import static br.rodrigues.compras.util.ConstantsApp.CATEGORIAS;

public class ItemAdapter extends BaseAdapter {

    private ListaItensActivity context;
    private List<Item> items;

    public ItemAdapter (ListaItensActivity context, List<Item> items){
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
    public View getView(int position, View view, ViewGroup viewGroup) {
        View viewCriada = criaViews(viewGroup);
        Item item = items.get(position);
        vincula(viewCriada, item);

        return viewCriada;
    }

    private void vincula(View view, Item item) {

        TextView nome = view.findViewById(R.id.item_campo_nome);
        nome.setText(item.getNome());

        TextView precoTotal = view.findViewById(R.id.item_campo_preco_total);
        precoTotal.setText(new ListaItensHelper().formatarEmReais(item.getPrecoTotal()));
        TextView precoTotalSimbulo = view.findViewById(R.id.item_preco_total_simbulo);

        TextView preco = view.findViewById(R.id.item_campo_preco);
        preco.setText(new ListaItensHelper().formatarEmReais(item.getPreco()));
        TextView precoSimbulo = view.findViewById(R.id.item_preco_simbulo);

        TextView quantidade = view.findViewById(R.id.item_campo_quantidade);
        quantidade.setText(String.valueOf(item.getQuantidade()));
        TextView simbuloMultiplica = view.findViewById(R.id.item_multiplica_simbulo);

        TextView categoria = view.findViewById(R.id.item_campo_categoria);
        categoria.setText(CATEGORIAS.get(item.getCategoria()));

        TextView dataComprado = view.findViewById(R.id.item_last_time);
        dataComprado.setText(formatDate(item.getDataCompra()));

        ImageButton btnEditar = view.findViewById(R.id.item_btn_editar);
        setupBtnEditar(btnEditar, item);

        ImageView iconItemImage = view.findViewById(R.id.item_image);
        iconItemImage.setImageResource(item.getCaminhoImagem());

        ConstraintLayout itemBackground = view.findViewById(R.id.item_background);

        if (item.getComprado()){
            itemBackground.setBackgroundColor(Color.LTGRAY);

            precoTotal.setTextColor(Color.GRAY);

            precoTotalSimbulo.setTextColor(Color.GRAY);

            quantidade.setTextColor(Color.GRAY);

            simbuloMultiplica.setTextColor(Color.GRAY);

            dataComprado.setTextColor(Color.GRAY);

            nome.setTextColor(Color.GRAY);

            preco.setTextColor(Color.GRAY);

            precoSimbulo.setTextColor(Color.GRAY);

            categoria.setTextColor(Color.GRAY);
//            nome.setPaintFlags(nome.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

    }

    private String formatDate(Long date) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date);
    }

    private void setupBtnEditar(final View view, final Item item) {

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popup = new PopupMenu(context, view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.item_menu_options, popup.getMenu());
                popup.show();


                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.item_menu_options_deletar:

                                new AlertDialog.Builder(context)
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setTitle("Atenção!")
                                        .setMessage("Você realmente deseja deletar o item " + item.getNome() + "?")
                                        .setNegativeButton("Não", null)
                                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                ItemDAO dao = new ItemDAO(context);
                                                dao.delete(item);

                                                context.onResume();

                                                Toast.makeText(context, "Item deletado", Toast.LENGTH_SHORT).show();
                                            }
                                        }).create().show();
                                return false;

                            case R.id.item_menu_options_editar:

                                Intent goToUpdate = new Intent(context, FormularioActivity.class);

                                goToUpdate.putExtra("itemToUpdate", item);

                                context.startActivity(goToUpdate);

                                return false;
                        }

                        return false;
                    }
                });
            }
        });
    }

    private View criaViews(ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.item, viewGroup, false);
    }

    public void update (List<Item> items){
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void simpleUpdate(){
        notifyDataSetChanged();
    }

    public void clear() {
        this.items.clear();
        notifyDataSetChanged();
    }

    public void add(Item item) {
        items.add(item);
        notifyDataSetChanged();
    }
}
