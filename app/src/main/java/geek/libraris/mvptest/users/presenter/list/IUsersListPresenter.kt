package geek.libraris.mvptest.users.presenter.list

interface IUsersListPresenter<V : UIUsersItemView> {
    var itemClickListener: ((V) -> Unit)?
    fun bindView(view: V)
    fun getCount(): Int
}

interface IUserListPresenter : IUsersListPresenter<UserUsersItemView>
