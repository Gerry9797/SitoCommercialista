/**
 * Modello paginazione di spring
 */
export interface PageModel<T> {
  content: T[]
  totalElements: number
  totalPages: number
  last: boolean
  first: boolean
  size: number
  empty: boolean
  number: number
  numberOfElements: number
}
