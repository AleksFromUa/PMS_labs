package ua.kpi.compsys.IO8120.ui.film

import ua.kpi.compsys.IO8120.R
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flatdialoglibrary.dialog.FlatDialog
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.android.material.snackbar.Snackbar
import java.lang.reflect.Field


class FilmFragment : Fragment() {

    private var columnCount = 1
    private var searchContainer: SearchContainer = SearchContainer()
    private lateinit var view: LinearLayout
    private lateinit var adapter: MyFilmRecyclerViewAdapter
    private lateinit var searchField: EditText
    private lateinit var addButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        view = inflater.inflate(R.layout.fragment_film_list, container, false) as LinearLayout
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        with(view.getChildAt(1) as RecyclerView) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
        }

        initFilms()
        initSearchField()
        val arr = searchContainer.search
        adapter = MyFilmRecyclerViewAdapter(requireContext(), arr){ item ->

            if (item.imdbID != null && item.imdbID!!.isNotEmpty()) {
                val id = getResId(item.imdbID!!, R.string::class.java)
                val jsonText = requireContext().resources.getString(id)
                val intent = Intent(requireContext(), DetailActivity::class.java)
                intent.putExtra("info", jsonText)
                startActivity(intent)
            }
        }
        view.findViewById<RecyclerView>(R.id.list).adapter = adapter
        enableSwipeToDeleteAndUndo()
        addButton = requireView().findViewById(R.id.add)
        addButton.setOnClickListener {
            val flatDialog = FlatDialog(requireContext())
            flatDialog.setTitle("New film")
                .setFirstTextFieldHint("Title")
                .setSecondTextFieldHint("Type")
                .setFirstButtonText("Add")
                .setSecondButtonText("Cancel")
                .withFirstButtonListner {
                    val search = Search()
                    search.title = flatDialog.firstTextField
                    search.type = flatDialog.secondTextField
                    searchContainer.search.add(search)
                    flatDialog.dismiss()
                }
                .withSecondButtonListner {
                    flatDialog.dismiss()
                }
                .show()
        }
    }

    private fun initFilms() {
        val mapper = ObjectMapper()
        val jsonText = requireView().resources.getString(R.string.json_films)
        searchContainer = mapper.readValue(jsonText, SearchContainer::class.java)
    }

    private fun initSearchField() {
        searchField = requireView().findViewById(R.id.filter)
        searchField.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable) {
                filter(s.toString())
            }
        })
    }

    fun filter(text: String) {
        val temp: MutableList<Search> = ArrayList()
        for (search in searchContainer.search) {
            if (search.title!!.contains(text)) {
                temp.add(search)
            }
        }
        adapter.updateList(temp)
    }

    private fun displaySnackBarWithBottomMargin(
        snackbar: Snackbar,
        sideMargin: Int,
        marginBottom: Int
    ) {
        val snackBarView = snackbar.view
        val params = snackBarView.layoutParams as CoordinatorLayout.LayoutParams
        params.setMargins(
            params.leftMargin + sideMargin,
            params.topMargin,
            params.rightMargin + sideMargin,
            params.bottomMargin + marginBottom
        )
        snackBarView.layoutParams = params
        snackbar.show()
    }

    private fun enableSwipeToDeleteAndUndo() {
        val swipeToDeleteCallback: SwipeToDeleteCallback = object : SwipeToDeleteCallback(
            requireContext()
        ) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {
                try {
                    val position = viewHolder.adapterPosition
                    val item: Search = adapter.getData().get(position)
                    val suppData: Search = searchContainer.search[position]
                    adapter.removeItem(position)
                    searchContainer.search.removeAt(position)
                    val snackbar: Snackbar = Snackbar
                        .make(
                            view,
                            "Film was deleted!",
                            Snackbar.LENGTH_LONG
                        )
                    snackbar.setAction("Undo", View.OnClickListener {
                        adapter.restoreItem(item, position)
                        searchContainer.search.add(position, suppData)
                        view.findViewById<RecyclerView>(R.id.list).scrollToPosition(position)
                    })
                    snackbar.setActionTextColor(Color.WHITE)
                    displaySnackBarWithBottomMargin(snackbar, 40, 40)
                } catch (ignored: Exception) {
                }
            }
        }
        val itemTouchhelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchhelper.attachToRecyclerView(view.findViewById<RecyclerView>(R.id.list))
    }

    fun getResId(resName: String, c: Class<*>): Int {
        return try {
            val idField: Field = c.getDeclaredField(resName)
            idField.getInt(idField)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            -1
        }
    }
}
