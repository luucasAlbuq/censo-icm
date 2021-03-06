package util;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import icm.censo.a3_code.com.censoicm.R;
import icm.censo.a3_code.com.censoicm.RelatorioDiaActivity;
import model.Censo;

/**
 * Created by luucasAlbuq on 14/06/2017.
 */
public class RecycleViewAdapter extends  RecyclerView.Adapter<RecycleViewAdapter.CensoViewHolder> {

    private List<Censo> censoList;
    private EstatisticaMes estatisticaMes;
    private Calendar calendar = Calendar.getInstance();

    public RecycleViewAdapter(List<Censo> lista){
        this.censoList = lista;
        this.estatisticaMes = EstatisticaMes.calculaEstatisticaMes(lista);
    }

    @Override
    public CensoViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        return new CensoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CensoViewHolder holder, final int position) {
        if(holder.censoDate != null && holder.censoIgreja != null){
            final Censo censo = censoList.get(position);
            Locale.setDefault(new Locale("pt", "BR"));
            SimpleDateFormat format = new SimpleDateFormat("dd-MMMM-yyyy");
            String data = format.format(censo.getData());
            holder.censoDate.setText(data);
            holder.censoIgreja.setText(censo.getIgreja());
            holder.censoTotal.setText("Total: "+String.valueOf(censo.getTotalPessoas()));
            holder.censoId.setText("ID: "+censo.getId());

            if(censo.getTotalPessoas() >= estatisticaMes.getMediaPessoas()){
                holder.censoIcone.setBackgroundResource(R.drawable.flag_green_icon);
            }else if(censo.getTotalPessoas()+5 <= estatisticaMes.getMediaPessoas()){
                holder.censoIcone.setBackgroundResource(R.drawable.flag_red_icon);
            }else{
                holder.censoIcone.setBackgroundResource(R.drawable.flag_orange_icon);
            }

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), RelatorioDiaActivity.class);
                    //To pass:
                    intent.putExtra(DBEsquema.TABLE.getValor(), censo);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return censoList.size();
    }

    public static class CensoViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private ImageView censoIcone;
        private TextView censoDate,censoIgreja,censoTotal, censoId;


        public CensoViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            censoDate = (TextView) itemView.findViewById(R.id.censo_date);
            censoIgreja = (TextView) itemView.findViewById(R.id.censo_igreja);
            censoIcone = (ImageView) itemView.findViewById(R.id.censo_icone);
            censoTotal = (TextView) itemView.findViewById(R.id.censo_total);
            censoId = (TextView) itemView.findViewById(R.id.censo_id);
        }
    }
}
