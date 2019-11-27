package com.github.klemenswiyanto.gymcomp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.klemenswiyanto.gymcomp.data.Gym
import com.github.klemenswiyanto.gymcomp.adapters.todoAdapter
import com.github.klemenswiyanto.gymcomp.viewmodels.DoesViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val ADD_DOES_REQUEST = 1
        const val EDIT_DOES_REQUEST = 2
    }

    private lateinit var doesViewModel: DoesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        doesViewModel = ViewModelProviders.of(this).get(DoesViewModel::class.java)

        buttonAddDoes.setOnClickListener {
            startActivityForResult(
                Intent(this, AddEditGymActivity::class.java),
                ADD_DOES_REQUEST
            )
        }

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)

        val adapter = todoAdapter()

        recycler_view.adapter = adapter

        doesViewModel.getAllDoes().observe(this, Observer<List<Gym>> {
            adapter.submitList(it)
        })

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                doesViewModel.delete(adapter.getDoesAt(viewHolder.adapterPosition))
                Toast.makeText(baseContext, "Gym Deleted!", Toast.LENGTH_SHORT).show()
            }
        }
        ).attachToRecyclerView(recycler_view)

        adapter.setOnItemClickListener(object : todoAdapter.OnItemClickListener {
            override fun onItemClick(gym: Gym) {
                val intent = Intent(baseContext, AddEditGymActivity::class.java)
                intent.putExtra(AddEditGymActivity.EXTRA_ID, gym.id)
                intent.putExtra(AddEditGymActivity.EXTRA_TITLE, gym.title)
                intent.putExtra(AddEditGymActivity.EXTRA_SETS, gym.sets)
                intent.putExtra(AddEditGymActivity.EXTRA_REPS, gym.reps)
                intent.putExtra(AddEditGymActivity.EXTRA_DUE_DATE, gym.duedate)

                startActivityForResult(intent, EDIT_DOES_REQUEST)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_DOES_REQUEST && resultCode == Activity.RESULT_OK) {
            val newDoes = Gym(
                data!!.getStringExtra(AddEditGymActivity.EXTRA_TITLE),
                data.getStringExtra(AddEditGymActivity.EXTRA_SETS),
                data.getStringExtra(AddEditGymActivity.EXTRA_REPS),
                data.getStringExtra(AddEditGymActivity.EXTRA_DUE_DATE)
            )
            doesViewModel.insert(newDoes)

            Toast.makeText(this, "Gym saved!", Toast.LENGTH_SHORT).show()
        } else if (requestCode == EDIT_DOES_REQUEST && resultCode == Activity.RESULT_OK) {
            val id = data?.getIntExtra(AddEditGymActivity.EXTRA_ID, -1)

            if (id == -1) {
                Toast.makeText(this, "Could not update! Error!", Toast.LENGTH_SHORT).show()
            }

            val updateDoes = Gym(
                data!!.getStringExtra(AddEditGymActivity.EXTRA_TITLE),
                data.getStringExtra(AddEditGymActivity.EXTRA_SETS),
                data.getStringExtra(AddEditGymActivity.EXTRA_REPS),
                data.getStringExtra(AddEditGymActivity.EXTRA_DUE_DATE)
            )
            updateDoes.id = data.getIntExtra(AddEditGymActivity.EXTRA_ID, -1)
            doesViewModel.update(updateDoes)

        } else {
            Toast.makeText(this, "Gym not saved!", Toast.LENGTH_SHORT).show()
        }


    }
}


