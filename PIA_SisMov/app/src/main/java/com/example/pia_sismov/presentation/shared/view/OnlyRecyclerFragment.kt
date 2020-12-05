package fcfm.lmad.poi.ChatPoi.presentation.shared.view

/*
class OnlyRecyclerFragment(
    private val ctx: Context
): BaseFragment(ctx), IChatContract.IChatListFrag.IView {

    lateinit var presenter: ChatListPresenter
    lateinit var adapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        presenter = ChatListPresenter(
            RetrieveChatUserList(ListAllUsers())
        )
        presenter.attachView(this)
        presenter.getListOfChats()
        return rootView
    }

    override fun getFragmentLayoutID(): Int  = R.layout.fragment_only_recycler

    override fun displayUsers(list: List<User>) {
        adapter = UserAdapter(list,false)
        rootView.rv_main_chat_frag.layoutManager = LinearLayoutManager(ctx)
        rootView.rv_main_chat_frag.adapter = adapter
    }
}*/