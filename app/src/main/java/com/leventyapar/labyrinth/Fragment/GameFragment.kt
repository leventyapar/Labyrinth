package com.leventyapar.labyrinth.Fragment

import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.viewbinding.ViewBindings
import com.leventyapar.labyrinth.Adapter.LabyrinthAdapter
import com.leventyapar.labyrinth.Model.LabyrinthItem
import com.leventyapar.labyrinth.R
import com.leventyapar.labyrinth.databinding.FragmentGameBinding


class GameFragment : Fragment() {

    private lateinit var binding : FragmentGameBinding
    private  var level = 0
    private  var length = 0
    private var square = 0
    private var cursor = 0
    private val  leftEdges = ArrayList<Int>()
    private val rightEdges = ArrayList<Int>()
    private var numOfSquares = 0
    var labyrinthList = ArrayList<LabyrinthItem>()
    var adapter : LabyrinthAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameBinding.inflate(layoutInflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        level = arguments?.getInt("level")!!
        length = arguments?.getInt("length")!!
        square = arguments?.getInt("square")!!

        numOfSquares = square * square
        createGame()
        adapter = LabyrinthAdapter(view.context, labyrinthList)
        adapter!!.notifyDataSetChanged()
        binding.apply {
            labyrinthGridview.adapter = adapter
            labyrinthGridview.layoutParams.height = (length * square ).dpToPixels(requireContext())
            labyrinthGridview.layoutParams.width = (length * square).dpToPixels(requireContext())
            labyrinthGridview.numColumns = square
            rightButton.setOnClickListener {
                if(!rightEdges.contains(cursor) && labyrinthList[cursor+1].type == "exit"){
                    createGame()
                    adapter!!.notifyDataSetChanged()
                }

               if(!rightEdges.contains(cursor) && labyrinthList[cursor + 1].movable == true){
                  labyrinthList[cursor].image = R.drawable.road
                  labyrinthList[cursor].movable = true
                  labyrinthList[cursor].type = "road"
                  cursor++
                  labyrinthList[cursor].image = R.drawable.player
                  labyrinthList[cursor].movable = false
                  labyrinthList[cursor].type = "player"

                  adapter!!.notifyDataSetChanged()
               }
            }

            leftButton.setOnClickListener {
                if(!leftEdges.contains(cursor) && labyrinthList[cursor-1].type == "exit"){
                    createGame()
                    adapter!!.notifyDataSetChanged()
                }
                if(!leftEdges.contains(cursor) && labyrinthList[cursor-1].movable == true){
                    labyrinthList[cursor].image = R.drawable.road
                    labyrinthList[cursor].movable = true
                    labyrinthList[cursor].type = "road"
                    cursor--
                    labyrinthList[cursor].image = R.drawable.player
                    labyrinthList[cursor].movable = false
                    labyrinthList[cursor].type = "player"
                    adapter!!.notifyDataSetChanged()
                }
            }

            upButton.setOnClickListener {
                if(cursor-square >=0 && labyrinthList[cursor-square].type == "exit"){
                    createGame()
                    adapter!!.notifyDataSetChanged()
                }
                if(cursor-square >=0 && labyrinthList[cursor-square].movable == true){
                    labyrinthList[cursor].image = R.drawable.road
                    labyrinthList[cursor].movable = true
                    labyrinthList[cursor].type = "road"
                    cursor = cursor - square
                    labyrinthList[cursor].image = R.drawable.player
                    labyrinthList[cursor].movable = false
                    labyrinthList[cursor].type = "player"
                    adapter!!.notifyDataSetChanged()
                }
            }

            downButton.setOnClickListener {
                if(cursor+square < labyrinthList.size && labyrinthList[cursor+square].type == "exit"){
                    createGame()
                    adapter!!.notifyDataSetChanged()
                }
                if(cursor+square < labyrinthList.size && labyrinthList[cursor+square].movable == true){
                    labyrinthList[cursor].image = R.drawable.road
                    labyrinthList[cursor].movable = true
                    labyrinthList[cursor].type = "road"
                    cursor = cursor + square
                    labyrinthList[cursor].image = R.drawable.player
                    labyrinthList[cursor].movable = false
                    labyrinthList[cursor].type = "player"
                    adapter!!.notifyDataSetChanged()
                }
            }


        }
    }


    fun createGame(){
        labyrinthList.clear()
        cursor = 0
        while (cursor < numOfSquares) {
            var wall = LabyrinthItem("wall",R.drawable.wall,false)
            labyrinthList.add(wall)
            cursor++
        }
        setLeftEdges()
        setRightEdges()
        createPath()
        setPlayerAndExit()
    }

    fun setPlayerAndExit() {
        labyrinthList[0].image = R.drawable.player
        labyrinthList[0].type = "player"
        labyrinthList[0].movable = false
        this.cursor = 0
        var lastIndex = 0

        for (i in 0..labyrinthList.size - 1) {
            if (labyrinthList[i].type == "road") {
                lastIndex = i
            }
        }
        labyrinthList[lastIndex].type = "exit"
        labyrinthList[lastIndex].movable = true
        labyrinthList[lastIndex].image = R.drawable.exit
    }

    fun createPath(){
        cursor = 0
        var whiteSquares = numOfSquares/2
        var direction = 0
        var times = 1
        while(whiteSquares > 0){

            for(i in 1..times){
                changeCursor(cursor,direction)
                if(labyrinthList[cursor].movable != true){
                    labyrinthList[cursor].image = R.drawable.road
                    labyrinthList[cursor].type = "road"
                    labyrinthList[cursor].movable = true
                    whiteSquares--
                }
            }
            direction = (1..4).random()
            times = (1..(square/2)).random()
        }
    }

    fun changeCursor(cursor: Int, direction: Int){
        if(direction == 1 && cursor-square >=0 ){
            this.cursor = cursor - square
        }
        else if(direction == 2 && cursor + square < labyrinthList.size ){
            this.cursor = cursor + square
        }
        else if(direction == 3 && !leftEdges.contains(cursor) ){
            this.cursor = cursor - 1
        }
        else if(direction == 4 && !rightEdges.contains(cursor)){
            this.cursor= cursor + 1
        }
    }



    fun Int.dpToPixels(context : Context):Int = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics
    ).toInt()

    fun setLeftEdges(){
        var edge = 0
        leftEdges.clear()
        while(edge < labyrinthList.size){
            leftEdges.add(edge)
            cursor++
            edge = edge + square
        }
    }

    fun setRightEdges(){
        var edge = square - 1
        rightEdges.clear()
        while(edge < labyrinthList.size){
            rightEdges.add(edge)
            edge = edge + square
        }
    }

}
