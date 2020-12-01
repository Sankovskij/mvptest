package geek.libraris.mvptest.repos.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import geek.libraris.mvptest.*
import geek.libraris.mvptest.users.model.entity.GithubUser
import geek.libraris.mvptest.repos.presenter.ReposPresenter
import geek.libraris.mvptest.repos.views.ReposView
import geek.libraris.mvptest.App
import geek.libraris.mvptest.common.BackButtonListener
import geek.libraris.mvptest.repos.model.retrofit.ReposApiHolder
import geek.libraris.mvptest.repos.model.retrofit.RetrofitGithubUserRepos
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_repos.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class ReposFragment : MvpAppCompatFragment(), ReposView, BackButtonListener {
    companion object {
        fun newInstance(data: GithubUser): ReposFragment {
            val reposFragment = ReposFragment()
            val bundle = Bundle()
            bundle.putParcelable("USER" , data)
            reposFragment.arguments = bundle
            return reposFragment
        }
    }


    val presenter: ReposPresenter by moxyPresenter { ReposPresenter(AndroidSchedulers.mainThread(),
                                                                    App.instance.router,
                                                                    arguments?.getParcelable("USER") as GithubUser?,
                                                                    RetrofitGithubUserRepos(ReposApiHolder.api)) }

    var adapter: ReposRVAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        View.inflate(context, R.layout.fragment_repos, null)

    override fun init() {
        rv_repos.layoutManager = LinearLayoutManager(context)
        adapter = ReposRVAdapter(presenter.reposListPresenter)
        rv_repos.adapter = adapter
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

    override fun backPressed() = presenter.backPressed()
}

