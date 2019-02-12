import Layout from '@/views/layout/Layout'

const workRouter = {
  path: '/work',
  component: Layout,
  redirect: 'noredirect',
  name: 'Work',
  meta: {
    title: 'Work',
    icon: 'tree'
  },
  children: [
    {
      path: 'work',
      component: () => import('@/views/work/index'),
      name: 'Work1',
      meta: { title: 'Work1' }
    }
  ]
}

export default workRouter
