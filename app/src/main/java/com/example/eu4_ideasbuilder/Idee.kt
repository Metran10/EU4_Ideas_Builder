package com.example.eu4_ideasbuilder

public class IdeaGroup(
    name: String,
    type: GroupType,
    ideas: ArrayList<Idea>,
    icon: Int,
    groupDescription: String)
{
    var name: String = name
    var type: GroupType = type
    var ideas: ArrayList<Idea> = ideas
    var icon: Int = icon
    var groupDescription: String = groupDescription

    public fun GetIdeaById(id: Int): Idea
    {
        return ideas[id];
    }

    public override fun toString(): String {
        return name
    }

}

public class Idea(
    name: String,
    bonuses: List<Bonus>,
    icon: Int)
{
    var name: String = name
    var bonuses: List<Bonus> = bonuses
    var icon: Int = icon

}

public class Bonus(
    bonusName: String,
    bonusType: BonusType,
    amount: Int,
    icon: Int
)
{
    var bonusName: String = bonusName
    var bonusType: BonusType = bonusType
    var amount: Int = amount
    var icon: Int = icon
}


enum class BonusType {FLAT, PERCENT, OTHER}
enum class GroupType {ADMINISTRATIVE, DYPLOMATIC, MILITARY}

public data class BonusData(var bonusName: String, var bonusAmount: Int, var bonusImage: Int)


