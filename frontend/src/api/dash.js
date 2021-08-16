import http from '../http'

export default {
  getAirtimeCount () {
    return http.get('/dash/airtime/count')
  },
  getAirtimeRequests () {
    return http.get('/dash/airtime')
  },
  getReviewCount () {
    return http.get('/dash/reviews/count')
  },
  getReviewRequests () {
    return http.get('/dash/reviews')
  },
  getKPLCTokenCount () {
    return http.get('/dash/kplc_token/count')
  },
  getKPLCTokenRequests () {
    return http.get('/dash/kplc_token')
  },
  getKPLCPostPaidCount () {
    return http.get('/dash/kplc_postpaid/count')
  },
  getKPLCPostPaidRequests () {
    return http.get('/dash/kplc_postpaid')
  },
  getBongaPointsCount () {
    return http.get('/dash/bongapoints/count')
  },
  getBongaPointsRequests () {
    return http.get('/dash/bongapoints')
  },
  getWaterCount () {
    return http.get('/dash/water/count')
  },
  getWaterRequests () {
    return http.get('/dash/water')
  },
  getPayTVCount () {
    return http.get('/dash/paytv/count')
  },
  getPayTVRequests () {
    return http.get('/dash/paytv')
  }
}
