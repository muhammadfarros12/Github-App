package com.farroos.githubapp.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.farroos.githubapp.R
import com.farroos.githubapp.databinding.ActivityDetailUserBinding
import com.farroos.githubapp.ui.detail.viewmodel.DetailUserViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_1,
            R.string.tab_2
        )
    }

    private lateinit var binding: ActivityDetailUserBinding

    private lateinit var viewModel: DetailUserViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        // kita akan membuat untuk bundle yg berguna untuk mengirimkan data tanpa menggunakan API
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        viewModel = ViewModelProvider(
            this
        ).get(DetailUserViewModel::class.java)

        username?.let { viewModel.setUserDetail(it) }

        viewModel.getUserDetail().observe(this, {
            if (it != null) {
                binding.apply {
                    txtName.text = it.name
                    txtUsername.text = it.login
                    txtFollowers.text = "${it.followers} Followers"
                    txtFollowing.text = "${it.following} Following"

                    Glide.with(this@DetailUserActivity)
                        .load(it.avatar_url)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .into(imgProfile)
                }
            }
        })

        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkedUser(id)
            withContext(Dispatchers.Main) {
                if (count != null) {
                    if (count > 0) {
                        binding.tglFavorite.isChecked = true
                        _isChecked = true
                    } else {
                        binding.tglFavorite.isChecked = false
                        _isChecked = false
                    }
                }
            }
        }

        binding.tglFavorite.setOnClickListener{
            _isChecked = !_isChecked
            if (_isChecked){
                if (username != null) {
                    viewModel.addToFavorite(username, id)
                }
            } else{
                viewModel.removeFromFavorite(id)
            }
            binding.tglFavorite.isChecked = _isChecked
        }

        val sectionPagerAdapter = SectionPagerAdapter(this, bundle)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

    }
}