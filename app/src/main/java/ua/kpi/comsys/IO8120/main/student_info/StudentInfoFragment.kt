package ua.kpi.comsys.IO8120.main.student_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ua.kpi.comsys.IO8120.core_ui.MainFragment
import ua.kpi.comsys.IO8120.main.databinding.FragmentStudentInfoBinding

class StudentInfoFragment : MainFragment() {
    private var _binding: FragmentStudentInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
