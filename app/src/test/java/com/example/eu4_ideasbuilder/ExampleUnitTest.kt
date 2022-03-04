package com.example.eu4_ideasbuilder

import io.mockk.every
import io.mockk.mockk
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }


    @Test
    fun sort_bonuses_test(){
        val bonus_comparator = object: Comparator<Bonus>{
            override fun compare(o1: Bonus, o2: Bonus): Int {
                when {
                    o1.bonusName > o2.bonusName -> return 1
                    o1.bonusName == o2.bonusName -> return 0
                    else -> return -1
                }
            }
        }

        val bon1 = Bonus("Colonist", BonusType.FLAT, 1, R.drawable.bonus_icon_colonist)
        val bon2 = Bonus("Diplomat", BonusType.FLAT, 1, R.drawable.bonus_icon_diplomat)
        val bon3 = Bonus("Yearly Naval Tradition", BonusType.FLAT, 1, R.drawable.bonus_icon_naval_tradition_from_protecting_trade)
        val bonArrTest = arrayListOf<Bonus>(bon2, bon3, bon1)
        bonArrTest.sortWith(bonus_comparator)

        assert(bonArrTest[0] == bon1)
        assert(bonArrTest[2] == bon3)

    }
    /*
    @Test
    fun group_add_test()
    {

        assert(Build.currentBuild.IdeaGroupsList.size == Build.currentBuild.currentSize)
        Build.currentBuild.AddGroup(Build.everyGroup[12])
        assert(Build.currentBuild.currentSize == 1)
    }
    */


    @Test
    fun group_add_max_test()
    {
        for(i: Int in 1..10)
        {
            Build.currentBuild.AddGroup(Build.everyGroup[i])
        }
        assert(Build.currentBuild.currentSize <= 8)
    }


    @Test
    fun group_add_test_with_mockk()
    {
        val build = mockk<Build>()

        every { build.currentSize } returns 0

        assert(build.currentSize == 0)

    }


    @Test
    fun change_name_with_mockk()
    {
        val build = mockk<Build>()

        every { build.buildName } returns "Nowa"

        assert(build.buildName == "Nowa")

    }


    @Test
    fun add_group_with_mockk()
    {
        val build = mockk<Build>()

        every { build.AddGroup(Build.everyGroup[1]) }

        for(i: Int in 1..10)
        {
            Build.currentBuild.AddGroup(Build.everyGroup[i])
        }
        every {build.currentSize} returns 7

        assert(build.currentSize <= 8)

    }










}