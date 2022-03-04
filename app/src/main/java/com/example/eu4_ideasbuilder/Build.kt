package com.example.eu4_ideasbuilder
import android.content.Context
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Toast
import java.io.File
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken


//@Serializable
class Build(val name: String = "New Build") {

    var buildName: String = name;
    var IdeaGroupsList: ArrayList<IdeaGroup> = ArrayList<IdeaGroup>();
    var currentSize: Int = 0;
    var totalBonusesList: ArrayList<Bonus> = ArrayList<Bonus>()


    public fun SetName(name: String){
        buildName = name;
    }

    public fun AddGroup(group: IdeaGroup){
        if(!(currentSize > 7))
        {
            if(!IsGroupPresent(group))
            {
                IdeaGroupsList.add(group);
                currentSize++;
                AddBonusesFromGroup(group)
            }
        }
    }



    public fun DeleteGroup(IdGrupy: Int){
        DelBonusesFromGroup(IdeaGroupsList[IdGrupy])
        IdeaGroupsList.removeAt(IdGrupy);
        currentSize--;
    }

    private fun AddBonusesFromGroup(group: IdeaGroup)
    {
        for(Idea in group.ideas)
        {
            for(bonus in Idea.bonuses)
            {
                val id: Int = totalBonusesList.indexOfFirst{e -> e.bonusName == bonus.bonusName};
                if(id == -1)
                {
                    totalBonusesList.add(bonus);
                }
                else{
                    totalBonusesList[id].amount += bonus.amount;
                }
            }
        }
        SortTotalBonuses()
    }

    private fun DelBonusesFromGroup(group: IdeaGroup){
        for(Idea in group.ideas) {
            for(bonus in Idea.bonuses)
            {
                val id: Int = totalBonusesList.indexOfFirst{e -> e.bonusName == bonus.bonusName};
                if(id == -1)
                {
                }
                else{
                    if(totalBonusesList[id].amount > bonus.amount)
                    {
                        totalBonusesList[id].amount -= bonus.amount
                    }
                    else{
                        totalBonusesList.remove(totalBonusesList[id])
                    }
                }
            }
        }
        SortTotalBonuses()
    }


    public fun SortTotalBonuses(){
        totalBonusesList.sortWith(object: Comparator<Bonus>{
            override fun compare(o1: Bonus, o2: Bonus): Int {
                when {
                    o1.bonusName > o2.bonusName -> return 1
                    o1.bonusName == o2.bonusName -> return 0
                    else -> return -1
                }
            }
        })
    }


    public fun IsGroupPresent(group: IdeaGroup): Boolean{

        for(gr in IdeaGroupsList){
            if(gr.name == group.name)
            {
                return true
            }
        }
        return false
    }





    companion object{
        var currentBuild: Build = Build()

        private lateinit var context: Context

        fun setContext(con: Context){
            context = con
        }

        fun getContext(): Context{
            return context
        }


        var savedbuilds: ArrayList<Build> = arrayListOf()

        public fun LoadBuilds()
        {
            val preferences = PreferenceManager.getDefaultSharedPreferences(Build.getContext())
            val jsonstring = preferences.getString("savedBuilds", null)

            if(jsonstring != null)
            {
                //savedbuilds = Gson().fromJson(jsonstring, arrayListOf<Build>().javaClass)
                savedbuilds = Gson().fromJson(jsonstring, object: TypeToken<ArrayList<Build>>(){}.type)
            }

        }

        public fun SaveWithoutCurrent()
        {
            val gson = Gson()
            val gsonPretty = GsonBuilder().setPrettyPrinting().create()

            val prefEdit = PreferenceManager.getDefaultSharedPreferences(Build.getContext()).edit()
            val jsonstring = gson.toJson(Build.savedbuilds)
            prefEdit.putString("savedBuilds", jsonstring).apply()
        }


        public fun SaveBuild()
        {


            for(bul in savedbuilds)
            {
                if(bul.buildName == currentBuild.buildName)
                {
                    Toast.makeText(getContext(), "Build of this name already exist. Please change it.", Toast.LENGTH_SHORT).show()
                    return
                }
            }

            savedbuilds.add(Build.currentBuild)

            val gson = Gson()
            //val gsonPretty = GsonBuilder().setPrettyPrinting().create()

            val prefEdit = PreferenceManager.getDefaultSharedPreferences(Build.getContext()).edit()
            val jsonstring = gson.toJson(Build.savedbuilds)
            prefEdit.putString("savedBuilds", jsonstring).apply()

            Toast.makeText(getContext(), "Build Saved", Toast.LENGTH_SHORT).show()

        }

        //0 - diplo, 1 -espio, 2 -explor, 3- influ, 4- maritime, 5- trade

        val dipGroups: List<IdeaGroup> = listOf<IdeaGroup>(
            IdeaGroup("Diplomatic", GroupType.DYPLOMATIC, arrayListOf(
                Idea("Foreign Embassies", arrayListOf(Bonus("Diplomat", BonusType.FLAT, 1,R.drawable.bonus_icon_diplomat)), R.drawable.bonus_icon_diplomat),
                Idea("Cabinet", arrayListOf(Bonus("Diplomatic relations", BonusType.FLAT, 1, R.drawable.bonus_icon_diplomatic_relations)), R.drawable.bonus_icon_diplomatic_relations),
                Idea("Grand Banquest", arrayListOf(Bonus("Diplomat", BonusType.FLAT, 1, R.drawable.bonus_icon_diplomat)), R.drawable.bonus_icon_diplomat),
                Idea("Benign Diplomats", arrayListOf(Bonus("Improve realtions", BonusType.PERCENT, 25, R.drawable.bonus_icon_improve_relations)), R.drawable.bonus_icon_improve_relations),
                Idea("Experienced Diplomats", arrayListOf(Bonus("Diplomatic reputation",BonusType.FLAT, 2, R.drawable.bonus_icon_diplomatic_reputation)), R.drawable.bonus_icon_diplomatic_reputation),
                Idea("Flexible Negotiations", arrayListOf(Bonus("Province warscore cost", BonusType.PERCENT, -20, R.drawable.bonus_icon_province_war_score_cost)), R.drawable.bonus_icon_province_war_score_cost),
                Idea("Diplomatic Corps", arrayListOf(Bonus("Diplomatic technology cost", BonusType.PERCENT, -10, R.drawable.bonus_icon_diplomatic_technology_cost)), R.drawable.bonus_icon_diplomatic_technology_cost),
                Idea("Bonus", arrayListOf(Bonus("Lowered impact on stability from diplomatic actions", BonusType.OTHER, 0, R.drawable.bonus_icon_bonus)), R.drawable.bonus_icon_bonus)),
                R.drawable.icon_igroup_dip_diplomatic, "dipDesc"),
            IdeaGroup("Espionage", GroupType.DYPLOMATIC, arrayListOf(
                Idea("Efficient Spies", arrayListOf(Bonus("Spy network construction", BonusType.PERCENT, 50, R.drawable.bonus_icon_spy_network_construction), Bonus("Advisor costs", BonusType.PERCENT, -10, R.drawable.bonus_icon_advisor_cost)), R.drawable.bonus_icon_spy_network_construction),
                Idea("Agent Training", arrayListOf(Bonus("Diplomat", BonusType.FLAT, 1, R.drawable.bonus_icon_diplomat)), R.drawable.bonus_icon_diplomat),
                Idea("Vetting", arrayListOf(Bonus("Foreign spy detection", BonusType.PERCENT, 33, R.drawable.bonus_icon_foreign_spy_detection)), R.drawable.bonus_icon_foreign_spy_detection),
                Idea("State Propaganda", arrayListOf(Bonus("Aggressive expansion impact", BonusType.PERCENT, -20,R.drawable.bonus_icon_aggressive_expansion_impact)), R.drawable.bonus_icon_aggressive_expansion_impact),
                Idea("Claim Fabrication", arrayListOf(Bonus("May fabricate claims for subjects", BonusType.OTHER, 0,R.drawable.bonus_icon_may_fabricate_claims_for_subjects), Bonus("Cost to fabricate claims", BonusType.PERCENT, -25, R.drawable.bonus_icon_cost_to_fabricate_claims)), R.drawable.bonus_icon_may_fabricate_claims_for_subjects),
                Idea("Privateers", arrayListOf(Bonus("Embargo efficiency", BonusType.PERCENT, 25, R.drawable.bonus_icon_embargo_efficiency), Bonus("Privateer efficiency", BonusType.PERCENT, 33, R.drawable.bonus_icon_privateer_efficiency)), R.drawable.bonus_icon_embargo_efficiency),
                Idea("Audit Checks", arrayListOf(Bonus("Yearly corruption", BonusType.FLAT, -1, R.drawable.bonus_icon_yearly_corruption)), R.drawable.bonus_icon_yearly_corruption),
                Idea("Bonus", arrayListOf(Bonus("Rebel support efficiency", BonusType.PERCENT, 50, R.drawable.bonus_icon_rebel_support_efficiency)), R.drawable.bonus_icon_bonus) ),
                R.drawable.icon_igroup_dip_espionage, "espionage description"),
            IdeaGroup("Exploration", GroupType.DYPLOMATIC, arrayListOf(
                Idea("Quest for the New World", arrayListOf(Bonus("Allows recruitment of explorers & conquistadors",BonusType.OTHER,0,R.drawable.bonus_icon_may_explore)),R.drawable.bonus_icon_may_explore),
                Idea("Colonial Ventures", arrayListOf( Bonus("Colonist",BonusType.FLAT,1,R.drawable.bonus_icon_colonist)), R.drawable.bonus_icon_colonist),
                Idea("Overseas Exploration", arrayListOf( Bonus("Colonial range",BonusType.PERCENT,50, R.drawable.bonus_icon_colonial_range)),R.drawable.bonus_icon_colonial_range),
                Idea("Land of Opportunity", arrayListOf(Bonus("Global settler increase",BonusType.FLAT,10, R.drawable.bonus_icon_global_settler_increase)), R.drawable.bonus_icon_global_settler_increase),
                Idea("Viceroys", arrayListOf(Bonus("Global tariffs",BonusType.PERCENT,10, R.drawable.bonus_icon_global_tariffs), Bonus("Envoy travel time",BonusType.PERCENT, -20, R.drawable.bonus_icon_envoy_travel_time)),R.drawable.bonus_icon_global_tariffs),
                Idea("Free Colonies",arrayListOf( Bonus("Expel minorities cost",BonusType.PERCENT,-100, R.drawable.bonus_icon_expel_minorities_cost)), R.drawable.bonus_icon_expel_minorities_cost),
                Idea("Global Empire",arrayListOf(Bonus("Naval force limit modifier", BonusType.PERCENT, 25, R.drawable.bonus_icon_naval_force_limit_modifier)), R.drawable.bonus_icon_naval_force_limit_modifier),
                Idea("Bonus",arrayListOf( Bonus("Can fabricate claim overseas in colonial regions",BonusType.OTHER,0, R.drawable.bonus_icon_may_fabricate_claims_for_subjects)), R.drawable.bonus_icon_bonus) ),
                R.drawable.icon_igroup_dip_exploration,"Description"),
            IdeaGroup("Influence", GroupType.DYPLOMATIC, arrayListOf(
                Idea("Tribute System", arrayListOf(Bonus("Income from vassals", BonusType.PERCENT, 25, R.drawable.bonus_icon_income_from_vassals)), R.drawable.bonus_icon_income_from_vassals),
                Idea("Additional Loyalist Recruitment", arrayListOf(Bonus("Liberty desire in subjects", BonusType.PERCENT, -15, R.drawable.bonus_icon_liberty_desire_in_subjects)), R.drawable.bonus_icon_liberty_desire_in_subjects),
                Idea("Integrated Elites", arrayListOf(Bonus("Diplomatic annexation cost", BonusType.PERCENT, -25, R.drawable.bonus_icon_diplomatic_annexation_cost)), R.drawable.bonus_icon_diplomatic_annexation_cost),
                Idea("Buffer States", arrayListOf(Bonus("Diplomatic relations", BonusType.FLAT, 1, R.drawable.bonus_icon_diplomatic_relations)), R.drawable.bonus_icon_diplomatic_relations),
                Idea("Diplomatic Influence", arrayListOf( Bonus("Diplomatic reputation", BonusType.FLAT, 2, R.drawable.bonus_icon_diplomatic_reputation)), R.drawable.bonus_icon_diplomatic_reputation),
                Idea("Postal Service", arrayListOf( Bonus("Envoy travel time", BonusType.PERCENT, -25, R.drawable.bonus_icon_envoy_travel_time)), R.drawable.bonus_icon_envoy_travel_time),
                Idea("Marcher Lords", arrayListOf( Bonus("Vassal force limit contribution", BonusType.PERCENT, 100, R.drawable.bonus_icon_vassal_force_limit_contribution)), R.drawable.bonus_icon_vassal_force_limit_contribution),
                Idea("Bonus", arrayListOf( Bonus("Unjustified demands", BonusType.PERCENT, -50, R.drawable.bonus_icon_unjustified_demands)), R.drawable.bonus_icon_bonus) ),
                R.drawable.icon_igroup_dip_influence, "influencedescr"),
            IdeaGroup("Maritime", GroupType.DYPLOMATIC, arrayListOf(
                Idea("Merchant Traditions", arrayListOf(Bonus("Naval tradition form protecting trade", BonusType.PERCENT, 100,R.drawable.bonus_icon_naval_tradition_from_protecting_trade)), R.drawable.bonus_icon_naval_tradition_from_protecting_trade),
                Idea("Merchant Marine", arrayListOf( Bonus("National sailors modifier", BonusType.PERCENT, 50, R.drawable.bonus_icon_national_sailors_modifier)), R.drawable.bonus_icon_national_sailors_modifier),
                Idea("Sheltered Ports", arrayListOf( Bonus("Global ship repair", BonusType.PERCENT, 10, R.drawable.bonus_icon_global_ship_repair), Bonus("Sailor maintenance", BonusType.PERCENT, -10, R.drawable.bonus_icon_sailor_maintenance)), R.drawable.bonus_icon_global_ship_repair),
                Idea("Grand Navy", arrayListOf( Bonus("Naval force limit modifier", BonusType.PERCENT, 50, R.drawable.bonus_icon_naval_force_limit_modifier)), R.drawable.bonus_icon_naval_force_limit_modifier),
                Idea("Ship's Penny", arrayListOf( Bonus("Ship costs", BonusType.PERCENT, -10, R.drawable.bonus_icon_ship_costs )), R.drawable.bonus_icon_ship_costs),
                Idea("Seahawks", arrayListOf( Bonus("Leader without upkeep", BonusType.FLAT, 1, R.drawable.bonus_icon_leaders_without_upkeep), Bonus("Admiral cost", BonusType.PERCENT, 25, R.drawable.bonus_icon_ship_costs)), R.drawable.bonus_icon_leaders_without_upkeep),
                Idea("Naval Fighting Instruction", arrayListOf( Bonus("Bloackade efficiency", BonusType.PERCENT, 50, R.drawable.bonus_icon_blockade_efficiency), Bonus("Privateer efficiency", BonusType.PERCENT, 25, R.drawable.bonus_icon_privateer_efficiency)), R.drawable.bonus_icon_blockade_efficiency),
                Idea("Bonus", arrayListOf( Bonus("Ships can repair when in coastal sea zones", BonusType.OTHER, 0, R.drawable.bonus_icon_bonus)), R.drawable.bonus_icon_bonus)),
                R.drawable.icon_igroup_dip_maritime, "martinimedesc"),
            IdeaGroup("Trade", GroupType.DYPLOMATIC, arrayListOf(
                Idea("Shrewd Commerce Practice", arrayListOf( Bonus("Global trade power", BonusType.PERCENT, 20, R.drawable.bonus_icon_global_trade_power)), R.drawable.bonus_icon_global_trade_power),
                Idea("Free Trade", arrayListOf( Bonus("Merchant", BonusType.FLAT, 1, R.drawable.bonus_icon_merchant)), R.drawable.bonus_icon_merchant),
                Idea("Merchant Adventures", arrayListOf( Bonus("Trade range", BonusType.PERCENT, 25,R.drawable.bonus_icon_trade_range)), R.drawable.bonus_icon_trade_range),
                Idea("National Trade Policy", arrayListOf( Bonus("Trade efficiency", BonusType.PERCENT, 10, R.drawable.bonus_icon_trade_efficiency)), R.drawable.bonus_icon_trade_efficiency),
                Idea("Overseas Merchants", arrayListOf( Bonus("Merchant", BonusType.FLAT, 1, R.drawable.bonus_icon_merchant)), R.drawable.bonus_icon_merchant),
                Idea("Trade Manipulation", arrayListOf( Bonus("Trade steering", BonusType.PERCENT, 25, R.drawable.bonus_icon_trade_steering)), R.drawable.bonus_icon_trade_steering),
                Idea("Fast Negotiations", arrayListOf( Bonus("Caravan power", BonusType.PERCENT, 25, R.drawable.bonus_icon_caravan_power)), R.drawable.bonus_icon_caravan_power),
                Idea("Bonus", arrayListOf( Bonus("Merchant", BonusType.FLAT, 1, R.drawable.bonus_icon_merchant)), R.drawable.bonus_icon_bonus)),
                R.drawable.icon_igroup_dip_trade, "TradeDesc")
        )


        // 0 - admin, 1 -economic, 2 - expansion, 3 - humanist, 4 - innovative, 5 -religious
        val admGroups: List<IdeaGroup> = arrayListOf(
            IdeaGroup("Administrative", GroupType.ADMINISTRATIVE, arrayListOf(
                Idea("Organized Mercenary Payments", arrayListOf(Bonus("Mercenary cost", BonusType.PERCENT, -10, R.drawable.bonus_icon_mercenary_cost)), R.drawable.bonus_icon_mercenary_cost),
                Idea("Adaptability", arrayListOf(Bonus("Core-creation cost", BonusType.PERCENT, -25, R.drawable.bonus_icon_core_creation_cost)), R.drawable.bonus_icon_core_creation_cost),
                Idea("Benefits for Mercenaries", arrayListOf(Bonus("Mercenary maintenance", BonusType.PERCENT, -15, R.drawable.bonus_icon_mercenary_maintenance)),R.drawable.bonus_icon_mercenary_maintenance),
                Idea("Bookkeeping", arrayListOf(Bonus("Interest per annum", BonusType.FLAT, -1, R.drawable.bonus_icon_interest_per_annum)), R.drawable.bonus_icon_interest_per_annum),
                Idea("Mercenary Recruitment", arrayListOf(Bonus("Mercenary manpower", BonusType.PERCENT, 25, R.drawable.bonus_icon_mercenary_manpower)), R.drawable.bonus_icon_mercenary_manpower),
                Idea("Aministrative Efficiency", arrayListOf(Bonus("Possible advisor", BonusType.FLAT, 1, R.drawable.bonus_icon_possible_advisors)), R.drawable.bonus_icon_possible_advisors),
                Idea("Civil Service", arrayListOf(Bonus("Administrative technology cost", BonusType.PERCENT, -10, R.drawable.bonus_icon_administrative_technology_cost)), R.drawable.bonus_icon_administrative_technology_cost),
                Idea("Bonus", arrayListOf(Bonus("Governing capacity modifier", BonusType.PERCENT, 25, R.drawable.bonus_icon_governing_capacity_modifier)), R.drawable.bonus_icon_bonus)),
                R.drawable.icon_igroup_adm_administrative, "Description"),
            IdeaGroup("Economic", GroupType.ADMINISTRATIVE, arrayListOf(
                Idea("Bureaucracy", arrayListOf(Bonus("National tax modifier", BonusType.PERCENT, 10, R.drawable.bonus_icon_national_tax_modifier)), R.drawable.bonus_icon_national_tax_modifier),
                Idea("Organized Construction", arrayListOf(Bonus("Construction cost", BonusType.PERCENT, -10, R.drawable.bonus_icon_construction_cost)),R.drawable.bonus_icon_construction_cost),
                Idea("National Bank", arrayListOf(Bonus("Yearly inflation reduction", BonusType.FLAT, 1, R.drawable.bonus_icon_yearly_inflation_reduction)),R.drawable.bonus_icon_yearly_inflation_reduction),
                Idea("Debt and Loans", arrayListOf(Bonus("Interest per annum", BonusType.FLAT, -1, R.drawable.bonus_icon_interest_per_annum)),R.drawable.bonus_icon_interest_per_annum),
                Idea("Centralization", arrayListOf(Bonus("Monthly autonomy change", BonusType.FLAT, -5, R.drawable.bonus_icon_autonomy)),R.drawable.bonus_icon_autonomy),
                Idea("Nationalistic Enthusiasm", arrayListOf(Bonus("Land maintenance modifier", BonusType.PERCENT, -5, R.drawable.bonus_icon_land_maintenance_modifier)),R.drawable.bonus_icon_land_maintenance_modifier),
                Idea("Smithian Economics", arrayListOf(Bonus("Production efficiency", BonusType.PERCENT, 10, R.drawable.bonus_icon_production_efficiency)),R.drawable.bonus_icon_production_efficiency),
                Idea("Bonus", arrayListOf(Bonus("Development cost", BonusType.PERCENT, -20, R.drawable.bonus_icon_development_cost)), R.drawable.bonus_icon_bonus)),
                R.drawable.icon_igroup_adm_economic, "Description"),
            IdeaGroup("Expansion", GroupType.ADMINISTRATIVE, arrayListOf(
               Idea("Additional Colonists", arrayListOf(Bonus("Colonist", BonusType.FLAT, 1, R.drawable.bonus_icon_colonist)),R.drawable.bonus_icon_colonist),
               Idea("Additional Merchants", arrayListOf(Bonus("Merchant", BonusType.FLAT, 1, R.drawable.bonus_icon_merchant)),R.drawable.bonus_icon_merchant),
               Idea("Faster Colonists", arrayListOf(Bonus("Global settler increase", BonusType.FLAT, 20, R.drawable.bonus_icon_global_settler_increase)),R.drawable.bonus_icon_global_settler_increase),
               Idea("Factories", arrayListOf(Bonus("Fort maintenance on border with rival", BonusType.PERCENT, -50, R.drawable.bonus_icon_fort_maintenance_on_border_with_rival), Bonus("Center of trade upgrade cost", BonusType.PERCENT, -20, R.drawable.bonus_icon_center_of_trade_upgrade_cost)),R.drawable.bonus_icon_fort_maintenance_on_border_with_rival),
                Idea("Additional Diplomats", arrayListOf(Bonus("Diplomatic relation", BonusType.FLAT, 1, R.drawable.bonus_icon_diplomatic_relations)),R.drawable.bonus_icon_diplomatic_relations),
                Idea("General Colonization Law", arrayListOf(Bonus("Colonist", BonusType.FLAT, 1, R.drawable.bonus_icon_colonist), Bonus("Settler chance", BonusType.PERCENT, 5, R.drawable.bonus_icon_settler_chance)), R.drawable.bonus_icon_colonist),
                Idea("Competitive Merchants", arrayListOf(Bonus("Global trade power", BonusType.PERCENT, 20, R.drawable.bonus_icon_global_trade_power)), R.drawable.bonus_icon_global_trade_power),
                Idea("Bonus", arrayListOf(Bonus("Minimum autonomy in territories", BonusType.PERCENT, -10, R.drawable.bonus_icon_minimum_autonomy_in_territories)), R.drawable.bonus_icon_bonus)),
                R.drawable.icon_igroup_adm_expansion, "Description"),
            IdeaGroup("Humanist", GroupType.ADMINISTRATIVE, arrayListOf(
              Idea("Tolerance", arrayListOf(Bonus("Religious unity", BonusType.PERCENT, 25, R.drawable.bonus_icon_religious_unity)),R.drawable.bonus_icon_religious_unity),
              Idea("Local Traditions", arrayListOf(Bonus("National unrest", BonusType.FLAT, -2, R.drawable.bonus_icon_national_unrest)),R.drawable.bonus_icon_national_unrest),
              Idea("Ecumenism", arrayListOf(Bonus("Tolerance of heretics", BonusType.FLAT, 2, R.drawable.bonus_icon_tolerance_heretic)), R.drawable.bonus_icon_tolerance_heretic),
              Idea("Indirect Rule", arrayListOf(Bonus("Years of seperatism", BonusType.FLAT, -10, R.drawable.bonus_icon_years_of_separatism)),R.drawable.bonus_icon_years_of_separatism),
              Idea("Cultural Ties", arrayListOf(Bonus("Max promoted cultures", BonusType.FLAT, 2, R.drawable.bonus_icon_max_promoted_cultures)), R.drawable.bonus_icon_max_promoted_cultures),
              Idea("Benevolence", arrayListOf(Bonus("Improve relations", BonusType.PERCENT, 30, R.drawable.bonus_icon_improve_relations)), R.drawable.bonus_icon_improve_relations),
              Idea("Humanist Tolerance", arrayListOf(Bonus("Tolerance of heathens", BonusType.FLAT, 2, R.drawable.bonus_icon_tolerance_heathen)), R.drawable.bonus_icon_tolerance_heathen),
              Idea("Bonus", arrayListOf(Bonus("Idea cost", BonusType.PERCENT, -10, R.drawable.bonus_icon_idea_cost)), R.drawable.bonus_icon_bonus)),
                R.drawable.icon_igroup_adm_humanist, "Descriptiuon"),
            IdeaGroup("Innovative", GroupType.ADMINISTRATIVE, arrayListOf(
                Idea("Patron of the Arts", arrayListOf(Bonus("Prestige decay", BonusType.PERCENT, -1, R.drawable.bonus_icon_prestige_decay)), R.drawable.bonus_icon_prestige_decay),
                Idea("Empiricism", arrayListOf(Bonus("Innovativeness gain", BonusType.PERCENT, 50, R.drawable.bonus_icon_innovativeness_gain)), R.drawable.bonus_icon_innovativeness_gain),
                Idea("Scientific Revolution", arrayListOf(Bonus("Technology cost", BonusType.PERCENT, -10, R.drawable.bonus_icon_administrative_technology_cost), Bonus("Institution embracement cost", BonusType.PERCENT, -10, R.drawable.bonus_icon_institution_embracement_cost)),R.drawable.bonus_icon_administrative_technology_cost),
                Idea("Dynamic Court", arrayListOf(Bonus("Possible advisor", BonusType.FLAT, 1, R.drawable.bonus_icon_possible_advisors)), R.drawable.bonus_icon_possible_advisors),
                Idea("Print Culture", arrayListOf(Bonus("Institution spread", BonusType.PERCENT, 25, R.drawable.bonus_icon_institution_spread)), R.drawable.bonus_icon_institution_spread),
                Idea("Optimism", arrayListOf(Bonus("Monthly war exhaustion", BonusType.FLAT, -5, R.drawable.bonus_icon_monthly_war_exhaustion)), R.drawable.bonus_icon_monthly_war_exhaustion),
                Idea("Formalized Officer Corps", arrayListOf(Bonus("Leader without upkeep", BonusType.FLAT, 1, R.drawable.bonus_icon_leaders_without_upkeep)), R.drawable.bonus_icon_leaders_without_upkeep),
                Idea("Bonus", arrayListOf(Bonus("Advisor cost", BonusType.PERCENT, -25, R.drawable.bonus_icon_advisor_cost)), R.drawable.bonus_icon_advisor_cost)),
                R.drawable.icon_igroup_adm_innovative, "Descr"),
            IdeaGroup("Religious", GroupType.ADMINISTRATIVE, arrayListOf(
                Idea("Missionary Schools", arrayListOf(Bonus("Misionary", BonusType.FLAT, 1, R.drawable.bonus_icon_missionary)), R.drawable.bonus_icon_missionary),
                Idea("Church Attendance Duty", arrayListOf(Bonus("Stability cost modifier", BonusType.PERCENT, -25, R.drawable.bonus_icon_stability_cost_modifier)), R.drawable.bonus_icon_stability_cost_modifier),
                Idea("Divine Supremacy", arrayListOf(Bonus("Missionary strength", BonusType.PERCENT, 3, R.drawable.bonus_icon_missionary_strength)), R.drawable.bonus_icon_missionary_strength),
                Idea("Devoutness", arrayListOf(Bonus("Tolerance of the true faith", BonusType.FLAT, 2, R.drawable.bonus_icon_tolerance_of_the_true_faith), Bonus("Yearly papal influence", BonusType.FLAT, 2, R.drawable.bonus_icon_papal_influence)), R.drawable.bonus_icon_papal_influence),
                Idea("Religious Tradition", arrayListOf(Bonus("Yearly prestige", BonusType.FLAT, 1, R.drawable.bonus_icon_yearly_prestige)), R.drawable.bonus_icon_yearly_prestige),
                Idea("Inquisition", arrayListOf(Bonus("Missionary maintenance cost", BonusType.PERCENT, -50, R.drawable.bonus_icon_missionary_maintenance_cost)), R.drawable.bonus_icon_missionary_maintenance_cost),
                Idea("Deus Vult", arrayListOf(Bonus("Permanent casus belli against neighboring heathens and heretics", BonusType.OTHER, 0, R.drawable.bonus_icon_cb_on_religious_enemies)), R.drawable.bonus_icon_cb_on_religious_enemies),
                Idea("Bonus", arrayListOf(Bonus("Culture conversion cost", BonusType.PERCENT, -25, R.drawable.bonus_icon_culture_conversion_cost)), R.drawable.bonus_icon_bonus)),
                R.drawable.icon_igroup_adm_religious, "Description")
        )

        // 0- aristo, 1- divine, 2- horde, 3-native, 4-pluto, 5 - def, 6 naval, 7 - offensive, 8 - quality, 9 - quantity
        val milGroups: List<IdeaGroup> = arrayListOf(
            IdeaGroup("Aristocratic", GroupType.MILITARY, arrayListOf(
                Idea("Noble Knights", arrayListOf(Bonus("Cavalary cost", BonusType.PERCENT, -10, R.drawable.bonus_icon_cavalry_cost), Bonus("Cavalary combat ability",BonusType.PERCENT, 10, R.drawable.bonus_icon_cavalry_combat_ability)), R.drawable.bonus_icon_cavalry_cost),
                Idea("Military Traditions", arrayListOf(Bonus("Military technology cost", BonusType.PERCENT, -10, R.drawable.bonus_icon_military_technology_cost)), R.drawable.bonus_icon_military_technology_cost),
                Idea("Local Nobility", arrayListOf(Bonus("Monthly autonomy change", BonusType.FLAT, -2, R.drawable.bonus_icon_autonomy), Bonus("Yearly absolutism", BonusType.FLAT,1, R.drawable.bonus_icon_yearly_absolutism)), R.drawable.bonus_icon_autonomy),
                Idea("Serfdom", arrayListOf(Bonus("National manpower modifier", BonusType.PERCENT, 33, R.drawable.bonus_icon_national_manpower_modifier)), R.drawable.bonus_icon_national_manpower_modifier),
                Idea("Noble Officers", arrayListOf(Bonus("Yearly army tradition decay", BonusType.PERCENT, -1, R.drawable.bonus_icon_yearly_army_tradition_decay), Bonus("Yearly navy tradition decay", BonusType.PERCENT, -1, R.drawable.bonus_icon_yearly_navy_tradition_decay)), R.drawable.bonus_icon_yearly_army_tradition_decay),
                Idea("International Diplomacy", arrayListOf(Bonus("Diplomat", BonusType.FLAT, 1, R.drawable.bonus_icon_diplomat), Bonus("Leader without upkeep", BonusType.FLAT, 1, R.drawable.bonus_icon_leaders_without_upkeep)), R.drawable.bonus_icon_diplomat),
                Idea("Noble Connections", arrayListOf(Bonus("Mercenary manpower", BonusType.PERCENT, 20, R.drawable.bonus_icon_mercenary_manpower)), R.drawable.bonus_icon_mercenary_manpower),
                Idea("Bonus", arrayListOf(Bonus("Leader siege", BonusType.FLAT, 1, R.drawable.bonus_icon_leader_siege), Bonus("Nobility loyalty equilibrium", BonusType.PERCENT, 10, R.drawable.bonus_icon_nobility_loyalty_equilibrium)), R.drawable.bonus_icon_bonus)),
                R.drawable.icon_igroup_mil_aristocratic, "desc"),
            IdeaGroup("Divine", GroupType.MILITARY, arrayListOf(
                Idea("Servants of God", arrayListOf(Bonus("Yearly devotion", BonusType.FLAT, 1, R.drawable.bonus_icon_yearly_devotion)), R.drawable.bonus_icon_yearly_devotion),
                Idea("By the Grace of God", arrayListOf(Bonus("Fire damage received", BonusType.PERCENT, -10, R.drawable.bonus_icon_fire_damage_received)), R.drawable.bonus_icon_fire_damage_received),
                Idea("Friends in High Places", arrayListOf(Bonus("Leader cost", BonusType.PERCENT, -20, R.drawable.bonus_icon_leaders_without_upkeep)), R.drawable.bonus_icon_leaders_without_upkeep),
                Idea("Flesh is weak", arrayListOf(Bonus("Morale of armies", BonusType.PERCENT, 10, R.drawable.bonus_icon_morale_of_armies)), R.drawable.bonus_icon_morale_of_armies),
                Idea("Alpha and Omega", arrayListOf(Bonus("Culture conversion cost", BonusType.PERCENT, -10, R.drawable.bonus_icon_culture_conversion_cost), Bonus("Cost of enforcing religion through war", BonusType.PERCENT, -10, R.drawable.bonus_icon_tolerance_heretic)), R.drawable.bonus_icon_culture_conversion_cost),
                Idea("Conviction of Sin", arrayListOf(Bonus("National unrest", BonusType.FLAT, -2, R.drawable.bonus_icon_national_unrest)), R.drawable.bonus_icon_national_unrest),
                Idea("Martyrs", arrayListOf(Bonus("National manpower modifier", BonusType.PERCENT, 15, R.drawable.bonus_icon_national_manpower_modifier)),R.drawable.bonus_icon_national_manpower_modifier),
                Idea("Bonus", arrayListOf(Bonus("Missionary strength vs heretics", BonusType.PERCENT, 1, R.drawable.bonus_icon_missionary_strength_vs_heretics), Bonus("Clergy loyalty equilibrium", BonusType.PERCENT, 10, R.drawable.bonus_icon_clergy_loyalty_equilibrium)), R.drawable.bonus_icon_bonus)),
                R.drawable.icon_igroup_mil_divine, "desc"),
            IdeaGroup("Horde Government", GroupType.MILITARY, arrayListOf(
                Idea("Horse-lords of the Steppes", arrayListOf(Bonus("Cavalary cost", BonusType.PERCENT, -33, R.drawable.bonus_icon_cavalry_cost)), R.drawable.bonus_icon_cavalry_cost),
                Idea("Beyond the Sun", arrayListOf(Bonus("Aggressive expansion impact", BonusType.PERCENT, -10, R.drawable.bonus_icon_aggressive_expansion_impact)), R.drawable.bonus_icon_aggressive_expansion_impact),
                Idea("Mandate of the Khan", arrayListOf(Bonus("Religious unity", BonusType.PERCENT, 25, R.drawable.bonus_icon_religious_unity)), R.drawable.bonus_icon_religious_unity),
                Idea("There Shall Be No Grass", arrayListOf(Bonus("Land attrition", BonusType.PERCENT, -20, R.drawable.bonus_icon_land_attrition)), R.drawable.bonus_icon_land_attrition),
                Idea("Horde Loyalty", arrayListOf(Bonus("National unrest", BonusType.FLAT, -1, R.drawable.bonus_icon_national_unrest)), R.drawable.bonus_icon_national_unrest),
                Idea("Watchers of the Silk Road", arrayListOf(Bonus("Caravan power", BonusType.PERCENT, 20, R.drawable.bonus_icon_caravan_power)), R.drawable.bonus_icon_caravan_power),
                Idea("A Magnanimous Empire", arrayListOf(Bonus("Max promoted cultures", BonusType.FLAT, 2, R.drawable.bonus_icon_max_promoted_cultures)), R.drawable.bonus_icon_max_promoted_cultures),
                Idea("Bonus", arrayListOf(Bonus("Cavalary combat ability", BonusType.PERCENT, 25, R.drawable.bonus_icon_cavalry_combat_ability)), R.drawable.bonus_icon_bonus)),
                R.drawable.icon_igroup_mil_horde, "desc"),
            IdeaGroup("Indigenous", GroupType.MILITARY, arrayListOf(
                Idea("Bountiful Land", arrayListOf(Bonus("Development cost", BonusType.PERCENT, -5, R.drawable.bonus_icon_development_cost), Bonus("Tribal development growth", BonusType.FLAT, 15, R.drawable.bonus_icon_tribal_development_growth)), R.drawable.bonus_icon_development_cost),
                Idea("Irregular warfare", arrayListOf(Bonus("Attrition for enemies", BonusType.FLAT, 1, R.drawable.bonus_icon_attrition_for_enemies)), R.drawable.bonus_icon_attrition_for_enemies),
                Idea("The Great Law", arrayListOf(Bonus("Monthly reform progress modifier", BonusType.PERCENT, 25, R.drawable.bonus_icon_monthly_reform_progress_modifier)), R.drawable.bonus_icon_monthly_reform_progress_modifier),
                Idea("Braves", arrayListOf(Bonus("Morale of armies", BonusType.PERCENT, 10, R.drawable.bonus_icon_morale_of_armies)), R.drawable.bonus_icon_morale_of_armies),
                Idea("Controlled Burns", arrayListOf(Bonus("National manpower modifier", BonusType.PERCENT, 20, R.drawable.bonus_icon_national_manpower_modifier)), R.drawable.bonus_icon_national_manpower_modifier),
                Idea("Trade with Foreigners", arrayListOf(Bonus("Idea cost", BonusType.PERCENT, -10, R.drawable.bonus_icon_idea_cost)), R.drawable.bonus_icon_idea_cost),
                Idea("Treaties Kept", arrayListOf(Bonus("Province warscore cost", BonusType.PERCENT, -20, R.drawable.bonus_icon_province_war_score_cost)), R.drawable.bonus_icon_province_war_score_cost),
                Idea("Bonus", arrayListOf(Bonus("Institution spread", BonusType.PERCENT, 25, R.drawable.bonus_icon_institution_spread)), R.drawable.bonus_icon_bonus)),
                R.drawable.icon_igroup_mil_idigenous, "desc"),
            IdeaGroup("Plutocratic", GroupType.MILITARY, arrayListOf(
                Idea("Tradition of Payment", arrayListOf(Bonus("Mercenary manpower", BonusType.PERCENT, 10, R.drawable.bonus_icon_mercenary_manpower), Bonus("Mercenary discipline", BonusType.PERCENT, 3, R.drawable.bonus_icon_mercenary_discipline)), R.drawable.bonus_icon_mercenary_manpower),
                Idea("Abolished Serfdom", arrayListOf(Bonus("Morale of armies", BonusType.PERCENT, 10, R.drawable.bonus_icon_morale_of_armies)), R.drawable.bonus_icon_morale_of_armies),
                Idea("Bill of Rights", arrayListOf(Bonus("National unrest", BonusType.FLAT, -2, R.drawable.bonus_icon_national_unrest)), R.drawable.bonus_icon_national_unrest),
                Idea("Free Merchants", arrayListOf(Bonus("Merchant", BonusType.FLAT, 1, R.drawable.bonus_icon_merchant)), R.drawable.bonus_icon_merchant),
                Idea("Free Subjects", arrayListOf(Bonus("Goods produced modifier", BonusType.PERCENT, 10, R.drawable.bonus_icon_goods_produced_modifier)), R.drawable.bonus_icon_goods_produced_modifier),
                Idea("Free Cities", arrayListOf(Bonus("Caravan power", BonusType.PERCENT, 25, R.drawable.bonus_icon_caravan_power)), R.drawable.bonus_icon_caravan_power),
                Idea("Emancipation", arrayListOf(Bonus("Manpower recovery speed", BonusType.PERCENT, 20, R.drawable.bonus_icon_manpower_recovery_speed)), R.drawable.bonus_icon_manpower_recovery_speed),
                Idea("Bonus", arrayListOf(Bonus("Institution spread", BonusType.PERCENT, 10, R.drawable.bonus_icon_institution_spread), Bonus("Burghers loyalty equilibrium", BonusType.PERCENT, 10, R.drawable.bonus_icon_burghers_loyalty_equilibrium)), R.drawable.bonus_icon_bonus)),
                R.drawable.icon_igroup_mil_plutocratic, "desc"),
            IdeaGroup("Defensive", GroupType.MILITARY, arrayListOf(
                Idea("Battlefield Commisions", arrayListOf(Bonus("Army tradition from battles", BonusType.PERCENT, 100, R.drawable.bonus_icon_prestige_from_land_battles)), R.drawable.bonus_icon_prestige_from_land_battles),
                Idea("Military Drill", arrayListOf(Bonus("Morale of armies", BonusType.PERCENT, 15, R.drawable.bonus_icon_morale_of_armies)), R.drawable.bonus_icon_morale_of_armies),
                Idea("Improved Maneuver", arrayListOf(Bonus("Land leader maneuver", BonusType.FLAT, 1, R.drawable.bonus_icon_land_leader_maneuver)), R.drawable.bonus_icon_land_leader_maneuver),
                Idea("Regimental System", arrayListOf(Bonus("Land maintenance modifier", BonusType.PERCENT, -5, R.drawable.bonus_icon_land_maintenance_modifier)), R.drawable.bonus_icon_land_maintenance_modifier),
                Idea("Defensive Mentality", arrayListOf(Bonus("Fort maintenance", BonusType.PERCENT, -10, R.drawable.bonus_icon_fort_maintenance), Bonus("Fort defense", BonusType.PERCENT, 20, R.drawable.bonus_icon_fort_defense)), R.drawable.bonus_icon_fort_maintenance),
                Idea("Supply Trains", arrayListOf(Bonus("Reinforce speed", BonusType.PERCENT, 33, R.drawable.bonus_icon_reinforce_speed)), R.drawable.bonus_icon_reinforce_speed),
                Idea("Improved Foraging", arrayListOf(Bonus("Land attrition", BonusType.PERCENT, -25, R.drawable.bonus_icon_land_attrition)), R.drawable.bonus_icon_land_attrition),
                Idea("Bonus", arrayListOf(Bonus("Attrition for enemies", BonusType.FLAT, 1, R.drawable.bonus_icon_attrition_for_enemies)), R.drawable.bonus_icon_bonus)),
                R.drawable.icon_igroup_mil_defensive, "Desc"),
            IdeaGroup("Naval", GroupType.MILITARY, arrayListOf(
                Idea("Boarding Parties", arrayListOf(Bonus("Naval leader shock", BonusType.FLAT, 1, R.drawable.bonus_icon_naval_leader_shock)), R.drawable.bonus_icon_naval_leader_shock),
                Idea("Improved Rams", arrayListOf(Bonus("Galley combat ability", BonusType.PERCENT, 25, R.drawable.bonus_icon_galley_combat_ability)), R.drawable.bonus_icon_galley_combat_ability),
                Idea("Naval Cadets", arrayListOf(Bonus("Naval leader fire", BonusType.FLAT, 1, R.drawable.bonus_icon_naval_leader_fire), Bonus("Morale hit when losing a ship", BonusType.PERCENT, -33, R.drawable.bonus_icon_morale_hit_when_losing_a_ship)), R.drawable.bonus_icon_naval_leader_fire),
                Idea("Naval Glory", arrayListOf(Bonus("Yearly navy tradition", BonusType.FLAT, 1, R.drawable.bonus_icon_yearly_navy_tradition)), R.drawable.bonus_icon_yearly_navy_tradition),
                Idea("Press Gangs", arrayListOf(Bonus("Sailor recovery speed", BonusType.PERCENT, 25, R.drawable.bonus_icon_sailor_recovery_speed)), R.drawable.bonus_icon_sailor_recovery_speed),
                Idea("Oak Forests for Ships", arrayListOf(Bonus("Heavy ship combat ability", BonusType.PERCENT, 20, R.drawable.bonus_icon_heavy_ship_combat_ability)), R.drawable.bonus_icon_heavy_ship_combat_ability),
                Idea("Superior Seamanship", arrayListOf(Bonus("Morale of navies", BonusType.PERCENT, 10, R.drawable.bonus_icon_morale_of_navies), Bonus("Global naval engagement", BonusType.PERCENT, 10, R.drawable.bonus_icon_global_naval_engagement)), R.drawable.bonus_icon_morale_of_navies),
                Idea("Bonus", arrayListOf(Bonus("Ship durability", BonusType.PERCENT, 10, R.drawable.bonus_icon_ship_durability), Bonus("Marines force limit", BonusType.PERCENT, 10, R.drawable.bonus_icon_marines_force_limit)), R.drawable.bonus_icon_bonus)),
                R.drawable.icon_igroup_mil_naval, "desc"),
            IdeaGroup("Offensive", GroupType.MILITARY, arrayListOf(
                Idea("Bayonet Leaders", arrayListOf(Bonus("Land leader shock", BonusType.FLAT, 1, R.drawable.bonus_icon_land_leader_shock)), R.drawable.bonus_icon_land_leader_shock),
                Idea("National Conscripts", arrayListOf(Bonus("Recruitment time", BonusType.PERCENT, -10, R.drawable.bonus_icon_recruitment_time)), R.drawable.bonus_icon_recruitment_time),
                Idea("Superior Firepower", arrayListOf(Bonus("Land leader fire", BonusType.FLAT, 1, R.drawable.bonus_icon_land_leader_fire)), R.drawable.bonus_icon_land_leader_fire),
                Idea("Glorious Arms", arrayListOf(Bonus("Prestige from land battles", BonusType.PERCENT, 100, R.drawable.bonus_icon_prestige_from_land_battles)), R.drawable.bonus_icon_prestige_from_land_battles),
                Idea("Engineer Corps", arrayListOf(Bonus("Siege ability", BonusType.PERCENT, 20, R.drawable.bonus_icon_siege_ability)), R.drawable.bonus_icon_siege_ability),
                Idea("Grand Army", arrayListOf(Bonus("Land force limit modifier", BonusType.PERCENT, 20, R.drawable.bonus_icon_land_force_limit_modifier)), R.drawable.bonus_icon_land_force_limit_modifier),
                Idea("Esprit de Corps", arrayListOf(Bonus("Discipline", BonusType.PERCENT, 5, R.drawable.bonus_icon_discipline)), R.drawable.bonus_icon_discipline),
                Idea("Bonus", arrayListOf(Bonus("Recover army morale speed", BonusType.PERCENT, 5, R.drawable.bonus_icon_recover_army_morale_speed)), R.drawable.bonus_icon_bonus)),
                R.drawable.icon_igroup_mil_offensive, "desc"),
            IdeaGroup("Quality", GroupType.MILITARY, arrayListOf(
                Idea("Private to Marshal", arrayListOf(Bonus("Infantry combat ability", BonusType.PERCENT, 10, R.drawable.bonus_icon_infantry_combat_ability)), R.drawable.bonus_icon_infantry_combat_ability),
                Idea("Quality Education", arrayListOf(Bonus("Yearly army tradition", BonusType.FLAT, 1, R.drawable.bonus_icon_yearly_army_tradition)), R.drawable.bonus_icon_yearly_army_tradition),
                Idea("Finest of Horses", arrayListOf(Bonus("Cavalary combat ability", BonusType.PERCENT, 10, R.drawable.bonus_icon_cavalry_combat_ability)), R.drawable.bonus_icon_cavalry_combat_ability),
                Idea("Corvettses", arrayListOf(Bonus("Ship durability", BonusType.PERCENT, 5, R.drawable.bonus_icon_ship_durability)), R.drawable.bonus_icon_ship_durability),
                Idea("Naval Drill", arrayListOf(Bonus("Morale of navies", BonusType.PERCENT, 10, R.drawable.bonus_icon_morale_of_navies)), R.drawable.bonus_icon_morale_of_navies),
                Idea("Copper Bottoms", arrayListOf(Bonus("Naval attrition", BonusType.PERCENT, -25, R.drawable.bonus_icon_naval_attrition)), R.drawable.bonus_icon_naval_attrition),
                Idea("Massed Battery", arrayListOf(Bonus("Artillery combat ability", BonusType.PERCENT, 10, R.drawable.bonus_icon_artillery_combat_ability)), R.drawable.bonus_icon_artillery_combat_ability),
                Idea("Bonus", arrayListOf(Bonus("Discipline", BonusType.PERCENT, 5, R.drawable.bonus_icon_discipline)), R.drawable.bonus_icon_bonus)),
                R.drawable.icon_igroup_mil_quality, "Desc"),
            IdeaGroup("Quantity", GroupType.MILITARY, arrayListOf(
                Idea("Levee en Masse", arrayListOf(Bonus("National manpower modifier", BonusType.PERCENT, 50, R.drawable.bonus_icon_national_manpower_modifier)), R.drawable.bonus_icon_national_manpower_modifier),
                Idea("The Young can Serve", arrayListOf(Bonus("Manpower recovery speed", BonusType.PERCENT, 20, R.drawable.bonus_icon_manpower_recovery_speed)), R.drawable.bonus_icon_manpower_recovery_speed),
                Idea("Enforced Service", arrayListOf(Bonus("Regiment cost", BonusType.PERCENT, -10, R.drawable.bonus_icon_regiment_cost)), R.drawable.bonus_icon_regiment_cost),
                Idea("The Old and Infirm", arrayListOf(Bonus("Land maintenance modifier", BonusType.PERCENT, -5, R.drawable.bonus_icon_land_maintenance_modifier)), R.drawable.bonus_icon_land_maintenance_modifier),
                Idea("Camp Followers", arrayListOf(Bonus("National supply limit modifier", BonusType.PERCENT, 33, R.drawable.bonus_icon_supply_limit)), R.drawable.bonus_icon_supply_limit),
                Idea("Conscripted Garrison", arrayListOf(Bonus("Garrison size", BonusType.PERCENT, 25, R.drawable.bonus_icon_garrison_size)), R.drawable.bonus_icon_garrison_size),
                Idea("Expanded Supply Trains", arrayListOf(Bonus("Land attrition", BonusType.PERCENT, -10, R.drawable.bonus_icon_land_attrition)), R.drawable.bonus_icon_land_attrition),
                Idea("Bonus", arrayListOf(Bonus("Land force limit modifier", BonusType.PERCENT, 50, R.drawable.bonus_icon_land_force_limit_modifier)), R.drawable.bonus_icon_bonus)),
                R.drawable.icon_igroup_mil_quantity, "desc")


        )

        val everyGroup: List<IdeaGroup> = admGroups + dipGroups + milGroups;





    }//object



}