import { HttpParams } from '@angular/common/http'
import { LazyLoadEvent } from 'primeng/api'

const addValueNotNull = (params: HttpParams, objValues: any): HttpParams => {
  const result = Object.keys(objValues).map((key) => [key, objValues[key]])
  result.forEach((element) => {
    if (typeof element[1] === 'string' && element[1]) {
      params = params.set(element[0], element[1])
    } else if (typeof element[1] === 'number' && (0 === element[1] || element[1])) {
      params = params.set(element[0], '' + element[1])
    } else {
      if (element[1] instanceof Date && element[1]) {
        params = params.set(element[0], element[1].toISOString())
      } else {
      }
    }
  })

  return params
}

const addGridFilter = (params: HttpParams, gridEvent: LazyLoadEvent): HttpParams => {
  params = params.set('offset', !gridEvent.first ? '0' : gridEvent.first.toString())
  params = params.set('limit', gridEvent.rows && gridEvent.rows > 0 ? gridEvent.rows.toString() : '10')
  if (gridEvent.sortField) {
    params = params.set('sort-field', gridEvent.sortField)
  }
  params = params.set('sort-direction', gridEvent.sortOrder && +gridEvent.sortOrder > 0 ? 'asc' : 'desc')
  return params
}

export { addValueNotNull, addGridFilter }
