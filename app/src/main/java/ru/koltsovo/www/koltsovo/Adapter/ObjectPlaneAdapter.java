package ru.koltsovo.www.koltsovo.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.koltsovo.www.koltsovo.ObjectPlane;
import ru.koltsovo.www.koltsovo.R;

public class ObjectPlaneAdapter extends BaseAdapter implements Filterable {

    private List<ObjectPlane> originalList;
    private List<ObjectPlane> filteredList;
    private LayoutInflater layoutInflater;
    private Context myContext;
    private ItemFilter itemsFilter = new ItemFilter();

    public ObjectPlaneAdapter(Context context, List<ObjectPlane> list) {
        myContext = context;
        this.originalList = list;
        this.filteredList = list;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    static class ViewHolder {
        private TextView itemFlight;
        private TextView itemDirection;
        private TextView itemType;
        private TextView itemTimePlan;
        private TextView itemTimeFact;
        private TextView itemStatus;
        private TextView itemGate;
        private TextView itemBaggageStatus;
        private TextView itemCheckIn;
        private TextView itemCombination;
        private TextView descriptionStatus;
        private TextView descriptionBaggage;
        private TextView descriptionGate;
        private TextView descriptionCheckIn;
        private RelativeLayout relativeLayout;
        private ImageView imageViewTracking;
        private ImageView imageViewLogo;
    }

    @Override
    public int getCount() {
        return filteredList.size();
    }

    @Override
    public Object getItem(int i) {
        return filteredList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void setInfoTracking (int i) {
        boolean tracking;
        ObjectPlane objectPlane = getObjectPlane(i);
        tracking = objectPlane.isPlaneTracking();
        objectPlane.setPlaneTracking(!tracking);
        notifyDataSetChanged();
    }

    public boolean getInfoTracking (int i) {
        boolean tracking;
        ObjectPlane objectPlane = getObjectPlane(i);
        tracking = objectPlane.isPlaneTracking();
        return tracking;
    }

    private void setInfoTrackingToTrue (int i) {
        ObjectPlane objectPlane = getObjectPlane(i);
        objectPlane.setPlaneTracking(true);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.listview_item, viewGroup, false);

            holder = new ViewHolder();

            holder.itemFlight = (TextView) view.findViewById(R.id.tvPlaneFlight);
            holder.itemDirection = (TextView) view.findViewById(R.id.tvPlaneDirection);
            holder.itemType = (TextView) view.findViewById(R.id.tvPlaneType);
            holder.itemTimePlan = (TextView) view.findViewById(R.id.tvPlaneTimePlan);
            holder.itemTimeFact = (TextView) view.findViewById(R.id.tvPlaneTimeFact);
            holder.itemStatus = (TextView) view.findViewById(R.id.tvPlaneStatus);
            holder.itemBaggageStatus = (TextView) view.findViewById(R.id.tvPlaneBaggage);
            holder.itemGate = (TextView) view.findViewById(R.id.tvPlaneGate);
            holder.itemCheckIn = (TextView) view.findViewById(R.id.tvPlaneCheckIn);
            holder.itemCombination = (TextView) view.findViewById(R.id.tvPlaneCombination);
            holder.descriptionStatus = (TextView) view.findViewById(R.id.tvPlaneStatusDesc);
            holder.descriptionBaggage = (TextView) view.findViewById(R.id.tvPlaneBaggageDesc);
            holder.descriptionGate = (TextView) view.findViewById(R.id.tvPlaneGateDesc);
            holder.descriptionCheckIn = (TextView) view.findViewById(R.id.tvPlaneCheckInDesc);
            holder.relativeLayout = (RelativeLayout) view.findViewById(R.id.listViewItem);
            holder.imageViewTracking = (ImageView) view.findViewById(R.id.imageTracking);
            holder.imageViewLogo = (ImageView) view.findViewById(R.id.imageLogo);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        ObjectPlane objectPlane = getObjectPlane(i);

        holder.itemFlight.setText(objectPlane.getPlaneFlight());
        holder.itemDirection.setText(objectPlane.getPlaneDirection());
        holder.itemType.setText(objectPlane.getPlaneType());
        holder.itemTimePlan.setText(objectPlane.getPlaneTimePlan());
        holder.itemTimeFact.setText(objectPlane.getPlaneTimeFact());
        holder.itemStatus.setText(objectPlane.getPlaneStatus());
        holder.itemBaggageStatus.setText(objectPlane.getPlaneBaggageStatus());
        holder.itemGate.setText(objectPlane.getPlaneGate());
        holder.itemCheckIn.setText(objectPlane.getPlaneCheckIn());
        holder.itemCombination.setText(objectPlane.getPlaneCombination());

        holder.itemFlight.setTag(objectPlane.getPlaneRoute());
        holder.itemDirection.setTag(objectPlane.getPlaneRouteStatus());
        holder.itemType.setTag(objectPlane.getRegistrationBegin());
        holder.itemTimePlan.setTag(objectPlane.getRegistrationEnd());
        holder.itemTimeFact.setTag(objectPlane.getCheckInStatus());
        holder.itemStatus.setTag(objectPlane.getBoardingEnd());
        holder.itemBaggageStatus.setTag(objectPlane.getBoardingStatus());

        if (objectPlane.getPlaneCombination() == null || objectPlane.getPlaneCombination().length() < 2) {
            holder.itemCombination.setVisibility(View.GONE);
        } else {
            holder.itemCombination.setVisibility(View.VISIBLE);
        }

        if (objectPlane.getPlaneBaggageStatus() == null || objectPlane.getPlaneBaggageStatus().length() < 2) {
            holder.descriptionBaggage.setVisibility(View.GONE);
            holder.itemBaggageStatus.setVisibility(View.GONE);
        } else {
            holder.descriptionBaggage.setVisibility(View.VISIBLE);
            holder.itemBaggageStatus.setVisibility(View.VISIBLE);
        }

        if (objectPlane.getPlaneGate() == null || objectPlane.getPlaneGate().length() < 2) {
            holder.descriptionGate.setVisibility(View.GONE);
            holder.itemGate.setVisibility(View.GONE);
        } else {
            holder.descriptionGate.setVisibility(View.VISIBLE);
            holder.itemGate.setVisibility(View.VISIBLE);
        }

        if (objectPlane.getPlaneCheckIn() == null || objectPlane.getPlaneCheckIn().length() < 2) {
            holder.descriptionCheckIn.setVisibility(View.GONE);
            holder.itemCheckIn.setVisibility(View.GONE);
        } else {
            holder.descriptionCheckIn.setVisibility(View.VISIBLE);
            holder.itemCheckIn.setVisibility(View.VISIBLE);
        }

        if (objectPlane.isPlaneTracking()) {
            holder.imageViewTracking.setVisibility(View.VISIBLE);
        } else  {
            holder.imageViewTracking.setVisibility(View.GONE);
        }

        if (holder.imageViewLogo.getVisibility() == View.GONE) {
            holder.imageViewLogo.setVisibility(View.VISIBLE);
        }

        if (holder.descriptionStatus.getVisibility() == View.GONE && holder.itemStatus.getVisibility() == View.GONE) {
            holder.descriptionStatus.setVisibility(View.VISIBLE);
            holder.itemStatus.setVisibility(View.VISIBLE);
        }

        switch (objectPlane.getShotPlaneFlight()) {
            case "DP":
                holder.imageViewLogo.setImageDrawable(ContextCompat.getDrawable(myContext, R.drawable.drawable_logo_pobeda));
                break;
            case "7R":
                holder.imageViewLogo.setImageDrawable(ContextCompat.getDrawable(myContext, R.drawable.drawable_logo_rusline));
                break;
            case "SU":
                holder.imageViewLogo.setImageDrawable(ContextCompat.getDrawable(myContext, R.drawable.drawable_logo_aeroflot));
                break;
            case "U6":
                holder.imageViewLogo.setImageDrawable(ContextCompat.getDrawable(myContext, R.drawable.drawable_logo_ural_airlines));
                break;
            case "KL":
                holder.imageViewLogo.setImageDrawable(ContextCompat.getDrawable(myContext, R.drawable.drawable_logo_klm));
                break;
            case "IB":
                holder.imageViewLogo.setImageDrawable(ContextCompat.getDrawable(myContext, R.drawable.drawable_logo_iberia));
                break;
            case "9U":
                holder.imageViewLogo.setImageDrawable(ContextCompat.getDrawable(myContext, R.drawable.drawable_logo_air_moldova));
                break;
            case "BA":
                holder.imageViewLogo.setImageDrawable(ContextCompat.getDrawable(myContext, R.drawable.drawable_logo_british_airways));
                break;
            case "S7":
                holder.imageViewLogo.setImageDrawable(ContextCompat.getDrawable(myContext, R.drawable.drawable_logo_s7_airlines));
                break;
            case "AB":
                holder.imageViewLogo.setImageDrawable(ContextCompat.getDrawable(myContext, R.drawable.drawable_logo_air_berlin));
                break;
            case "TP":
                holder.imageViewLogo.setImageDrawable(ContextCompat.getDrawable(myContext, R.drawable.drawable_logo_tap_portugal));
                break;
            case "EY":
                holder.imageViewLogo.setImageDrawable(ContextCompat.getDrawable(myContext, R.drawable.drawable_logo_etihad_airways));
                break;
            case "YC":
                holder.imageViewLogo.setImageDrawable(ContextCompat.getDrawable(myContext, R.drawable.drawable_logo_ymal));
                break;
            case "KO":
                holder.imageViewLogo.setImageDrawable(ContextCompat.getDrawable(myContext, R.drawable.drawable_logo_komiaviatrans));
                break;
            case "AF":
                holder.imageViewLogo.setImageDrawable(ContextCompat.getDrawable(myContext, R.drawable.drawable_logo_air_france));
                break;
            case "A3":
                holder.imageViewLogo.setImageDrawable(ContextCompat.getDrawable(myContext, R.drawable.drawable_logo_aegean_airlines));
                break;
            case "LY":
                holder.imageViewLogo.setImageDrawable(ContextCompat.getDrawable(myContext, R.drawable.drawable_logo_el_al));
                break;
            case "UT":
                holder.imageViewLogo.setImageDrawable(ContextCompat.getDrawable(myContext, R.drawable.drawable_logo_utair));
                break;
            case "FV":
                holder.imageViewLogo.setImageDrawable(ContextCompat.getDrawable(myContext, R.drawable.drawable_logo_rossia));
                break;
            case "R2":
                holder.imageViewLogo.setImageDrawable(ContextCompat.getDrawable(myContext, R.drawable.drawable_logo_orenair));
                break;
            case "J2":
                holder.imageViewLogo.setImageDrawable(ContextCompat.getDrawable(myContext, R.drawable.drawable_logo_azal));
                break;
            case "OK":
                holder.imageViewLogo.setImageDrawable(ContextCompat.getDrawable(myContext, R.drawable.drawable_logo_czech_airlines));
                break;
            case "AZ":
                holder.imageViewLogo.setImageDrawable(ContextCompat.getDrawable(myContext, R.drawable.drawable_logo_alitalia));
                break;
            case "B2":
                holder.imageViewLogo.setImageDrawable(ContextCompat.getDrawable(myContext, R.drawable.drawable_logo_belavia));
                break;
            case "AY":
                holder.imageViewLogo.setImageDrawable(ContextCompat.getDrawable(myContext, R.drawable.drawable_logo_finnair));
                break;
            case "O7":
                holder.imageViewLogo.setImageDrawable(ContextCompat.getDrawable(myContext, R.drawable.drawable_logo_orenburgie));
                break;
            case "Y7":
                holder.imageViewLogo.setImageDrawable(ContextCompat.getDrawable(myContext, R.drawable.drawable_logo_nordstar_airlines));
                break;
            case "KC":
                holder.imageViewLogo.setImageDrawable(ContextCompat.getDrawable(myContext, R.drawable.drawable_logo_air_astana));
                break;
            case "FZ":
                holder.imageViewLogo.setImageDrawable(ContextCompat.getDrawable(myContext, R.drawable.drawable_logo_fly_dubai));
                break;
            case "4G":
                holder.imageViewLogo.setImageDrawable(ContextCompat.getDrawable(myContext, R.drawable.drawable_logo_gazpromavia));
                break;
            case "7J":
                holder.imageViewLogo.setImageDrawable(ContextCompat.getDrawable(myContext, R.drawable.drawable_logo_tajikair));
                break;
            case "ZF":
                holder.imageViewLogo.setImageDrawable(ContextCompat.getDrawable(myContext, R.drawable.drawable_logo_azur_air));
                break;
            case "6R":
                holder.imageViewLogo.setImageDrawable(ContextCompat.getDrawable(myContext, R.drawable.drawable_logo_alrosa));
                break;
            case "D9":
                holder.imageViewLogo.setImageDrawable(ContextCompat.getDrawable(myContext, R.drawable.drawable_logo_donavia));
                break;
            case "JL":
                holder.imageViewLogo.setImageDrawable(ContextCompat.getDrawable(myContext, R.drawable.drawable_logo_japan_airlines));
                break;
            case "5B":
                holder.imageViewLogo.setImageDrawable(ContextCompat.getDrawable(myContext, R.drawable.drawable_logo_euro_asia_air));
                break;
            case "6Z":
                holder.imageViewLogo.setImageDrawable(ContextCompat.getDrawable(myContext, R.drawable.drawable_logo_euro_asia_air));
                break;
            case "GH":
                holder.imageViewLogo.setImageDrawable(ContextCompat.getDrawable(myContext, R.drawable.drawable_logo_globus));
                break;
            case "TK":
                holder.imageViewLogo.setImageDrawable(ContextCompat.getDrawable(myContext, R.drawable.drawable_logo_turkish_airlines));
                break;
            case "ZM":
                holder.imageViewLogo.setImageDrawable(ContextCompat.getDrawable(myContext, R.drawable.drawable_logo_air_manas));
                break;
            case "R3":
                holder.imageViewLogo.setImageDrawable(ContextCompat.getDrawable(myContext, R.drawable.drawable_logo_yakutia));
                break;
            case "SZ":
                holder.imageViewLogo.setImageDrawable(ContextCompat.getDrawable(myContext, R.drawable.drawable_logo_somon_air));
                break;
            case "YK":
                holder.imageViewLogo.setImageDrawable(ContextCompat.getDrawable(myContext, R.drawable.drawable_logo_avia_traffic_company));
                break;
            case "HY":
                holder.imageViewLogo.setImageDrawable(ContextCompat.getDrawable(myContext, R.drawable.drawable_logo_uzbekistan_airways));
                break;
            case "4R":
                holder.imageViewLogo.setImageDrawable(ContextCompat.getDrawable(myContext, R.drawable.drawable_logo_royal_flight));
                break;
            case "RU":
                holder.imageViewLogo.setImageDrawable(ContextCompat.getDrawable(myContext, R.drawable.drawable_logo_air_bridge_cargo));
                break;
            case "ZG":
                holder.imageViewLogo.setImageDrawable(ContextCompat.getDrawable(myContext, R.drawable.drawable_logo_grozny_avia));
                break;
            default:
                holder.imageViewLogo.setVisibility(View.GONE);
                break;
        }

        switch (objectPlane.getPlaneStatus()) {
            case "Прибыл":
                holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(myContext, R.color.colorBackgroundGreen));
                break;
            case "Вылетел":
                holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(myContext, R.color.colorBackgroundGreen));
                holder.descriptionGate.setVisibility(View.GONE);
                holder.itemGate.setVisibility(View.GONE);
                break;
            case "Идет посадка":
                holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(myContext, R.color.colorBackgroundGreen));
                if (holder.descriptionGate.getVisibility() == View.GONE && holder.itemGate.getVisibility() == View.GONE) {
                    holder.descriptionGate.setVisibility(View.VISIBLE);
                    holder.itemGate.setVisibility(View.VISIBLE);
                }
                break;
            case "Идет регистрация":
                holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(myContext, R.color.colorBackgroundGreen));
                if (holder.descriptionGate.getVisibility() == View.GONE && holder.itemGate.getVisibility() == View.GONE) {
                    holder.descriptionGate.setVisibility(View.VISIBLE);
                    holder.itemGate.setVisibility(View.VISIBLE);
                }
                break;
            case "Отмена":
                holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(myContext, R.color.colorBackgroundRed));
                holder.descriptionGate.setVisibility(View.GONE);
                holder.itemGate.setVisibility(View.GONE);
                break;
            case "Регистрация закончена":
                holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(myContext, R.color.colorBackgroundYellow));
                if (holder.descriptionGate.getVisibility() == View.GONE && holder.itemGate.getVisibility() == View.GONE) {
                    holder.descriptionGate.setVisibility(View.VISIBLE);
                    holder.itemGate.setVisibility(View.VISIBLE);
                }
                break;
            case "Задержка":
                holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(myContext, R.color.colorBackgroundYellow));
                if (holder.descriptionGate.getVisibility() == View.GONE && holder.itemGate.getVisibility() == View.GONE) {
                    holder.descriptionGate.setVisibility(View.VISIBLE);
                    holder.itemGate.setVisibility(View.VISIBLE);
                }
                break;
            case "Задержка Позднее прибытие":
                holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(myContext, R.color.colorBackgroundYellow));
                if (holder.descriptionGate.getVisibility() == View.GONE && holder.itemGate.getVisibility() == View.GONE) {
                    holder.descriptionGate.setVisibility(View.VISIBLE);
                    holder.itemGate.setVisibility(View.VISIBLE);
                }
                break;
            case "Задержка Решение АК":
                holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(myContext, R.color.colorBackgroundYellow));
                if (holder.descriptionGate.getVisibility() == View.GONE && holder.itemGate.getVisibility() == View.GONE) {
                    holder.descriptionGate.setVisibility(View.VISIBLE);
                    holder.itemGate.setVisibility(View.VISIBLE);
                }
                break;
            case "Задержка Метеоусловия":
                holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(myContext, R.color.colorBackgroundYellow));
                if (holder.descriptionGate.getVisibility() == View.GONE && holder.itemGate.getVisibility() == View.GONE) {
                    holder.descriptionGate.setVisibility(View.VISIBLE);
                    holder.itemGate.setVisibility(View.VISIBLE);
                }
                break;
            case "Задержка Подготовка рейса":
                holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(myContext, R.color.colorBackgroundYellow));
                if (holder.descriptionGate.getVisibility() == View.GONE && holder.itemGate.getVisibility() == View.GONE) {
                    holder.descriptionGate.setVisibility(View.VISIBLE);
                    holder.itemGate.setVisibility(View.VISIBLE);
                }
                break;
            case "Задержка Регламент АП":
                holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(myContext, R.color.colorBackgroundYellow));
                if (holder.descriptionGate.getVisibility() == View.GONE && holder.itemGate.getVisibility() == View.GONE) {
                    holder.descriptionGate.setVisibility(View.VISIBLE);
                    holder.itemGate.setVisibility(View.VISIBLE);
                }
                break;
            case "Посадка закончена":
                holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(myContext, R.color.colorBackgroundYellow));
                holder.descriptionGate.setVisibility(View.GONE);
                holder.itemGate.setVisibility(View.GONE);
                break;
            default:
                holder.descriptionStatus.setVisibility(View.GONE);
                holder.itemStatus.setVisibility(View.GONE);
                holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(myContext, R.color.colorText));
                break;
            }
        return view;
    }

    private ObjectPlane getObjectPlane(int i) {
        return (ObjectPlane)getItem(i);
    }

    public Filter getFilter() {
        return itemsFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterString = constraint.toString().toLowerCase();
            FilterResults results = new FilterResults();
            int count = originalList.size();

            String filterableStringFlight;
            String filterableStringDirection;

            List<ObjectPlane> listWithFilter = new ArrayList<>();

            for (int i = 0; i < count; i++) {
                filterableStringFlight = originalList.get(i).getPlaneFlight();
                filterableStringDirection = originalList.get(i).getPlaneDirection();

                if (filterableStringFlight.toLowerCase().contains(filterString) || filterableStringDirection.toLowerCase().contains(filterString)) {
                    listWithFilter.add(originalList.get(i));
                }
            }
            results.values = listWithFilter;

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredList = (List<ObjectPlane>) results.values;
            notifyDataSetChanged();
        }
    }

    public void setTrackingInfoFromServer(String sentFlight, String sentTimePlan) {
        int count = originalList.size();

        for (int i = 0; i < count; i++) {
            String flight = originalList.get(i).getPlaneFlight();
            String timePlan = originalList.get(i).getPlaneTimePlan();

            if (flight.contains(sentFlight) && timePlan.contains(sentTimePlan)) {
                setInfoTrackingToTrue(i);
            }
        }
    }
}
