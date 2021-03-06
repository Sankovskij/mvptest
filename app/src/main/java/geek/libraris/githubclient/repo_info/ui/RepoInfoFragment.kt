package geek.libraris.githubclient.repo_info.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import geek.libraris.githubclient.*
import geek.libraris.githubclient.users.model.entity.GithubUser
import geek.libraris.githubclient.App
import geek.libraris.githubclient.common.BackButtonListener
import geek.libraris.githubclient.common.glide.GlideImageLoader
import geek.libraris.githubclient.common.glide.IImageLoader
import geek.libraris.githubclient.repo_info.presenter.RepoInfoPresenter
import geek.libraris.githubclient.repo_info.views.RepoInfoView
import geek.libraris.githubclient.repos.model.entity.GithubRepository
import kotlinx.android.synthetic.main.fragment_repo_info.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class RepoInfoFragment : MvpAppCompatFragment(), RepoInfoView, BackButtonListener {

    @Inject
    lateinit var imageLoader: IImageLoader<ImageView>

    companion object {
        fun newInstance(repo: GithubRepository?, user: GithubUser?): RepoInfoFragment {
            val repoFragment = RepoInfoFragment()
            val bundle = Bundle()
            bundle.putParcelable("USER" , user)
            bundle.putParcelable("REPO" , repo)
            repoFragment.arguments = bundle
            return repoFragment
        }
    }


    val presenter: RepoInfoPresenter by moxyPresenter { RepoInfoPresenter().apply {
        App.instance.appComponent.inject(this)
    }}



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            App.instance.appComponent.inject(this)
            return View.inflate(context, R.layout.fragment_repo_info, null)
        }


    override fun init() {

        val repo = arguments?.getParcelable("REPO") as GithubRepository?
        val user = arguments?.getParcelable("USER") as GithubUser?
        login.text = user?.login
        user?.avatarUrl?.let { imageLoader.loadInto(it, avatar) }
        name.text= repo?.name
        description.text= repo?.description
        size.text= repo?.size.toString()
        language.text= repo?.language



    }

    override fun backPressed() = presenter.backPressed()
}

