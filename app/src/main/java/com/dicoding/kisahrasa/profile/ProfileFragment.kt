package com.dicoding.kisahrasa.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.signature.ObjectKey
import com.dicoding.kisahrasa.R
import com.dicoding.kisahrasa.auth.AuthActivity
import com.dicoding.kisahrasa.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val profileViewModel: ProfileViewModel by viewModels()

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val getImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            saveImageToInternalStorage(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeState()
        setupAction()
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileViewModel.uiState.collect { state ->
                    binding.tvProfileName.text = state.name
                    binding.tvProfileEmail.text = state.email
                    
                    if (state.profileImagePath.isNotEmpty()) {
                        val file = File(state.profileImagePath)
                        if (file.exists()) {
                            Glide.with(this@ProfileFragment)
                                .load(file)
                                .signature(ObjectKey(file.lastModified()))
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .circleCrop()
                                .into(binding.ivProfilePicture)
                        }
                    }
                }
            }
        }
    }

    private fun setupAction() {
        binding.btnChangePhoto.setOnClickListener {
            getImage.launch("image/*")
        }

        binding.btnLogout.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                // 1. Clear our manual session in DataStore first
                profileViewModel.logout()
                
                // 2. Clear Firebase session
                FirebaseAuth.getInstance().signOut()
                
                // 3. Navigate back to AuthActivity and clear task stack
                val intent = Intent(requireActivity(), AuthActivity::class.java)
                startActivity(intent)
                requireActivity().finishAffinity()
            }
        }
    }

    private fun saveImageToInternalStorage(uri: Uri) {
        try {
            val inputStream: InputStream? = requireContext().contentResolver.openInputStream(uri)
            val file = File(requireContext().filesDir, "profile_picture.jpg")
            val outputStream = FileOutputStream(file)
            
            inputStream?.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }
            
            profileViewModel.saveProfileImagePath(file.absolutePath)
            Toast.makeText(context, getString(R.string.msg_photo_updated), Toast.LENGTH_SHORT).show()
            
        } catch (e: Exception) {
            Toast.makeText(context, "${getString(R.string.msg_photo_update_failed)} ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
