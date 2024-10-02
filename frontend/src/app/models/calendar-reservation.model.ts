export interface GiornoDelMese {
    day: number,
    month: number,
    year: number,
    monthState: string,
    enabled: boolean,
    occupancyPercentage?: number
    orariPrenotazione?: string[],
    isToday?: boolean
  }
