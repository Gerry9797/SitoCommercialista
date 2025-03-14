export const SITE_CONFIG = {
    revisoreLegale: false,
    datiPersonali: {
        nome: "Aisia",
        cognome: "Autiero",
        contatti: {
            email: "info@latuacommercialista.it",
            telefono: "3246871084",
            linkedin: "https://www.linkedin.com/in/aisia-autiero-6542b5250/",
            instagram: "https://www.instagram.com/la.tua.commercialista/",
            sitoWeb: "www.latuacommercialista.it",
            dominio: "latuacommercialista.it",
            sitoWebHttps: "https://latuacommercialista.it"
        },
        sede: {
            cap: "84098",
            localita: "Pontecagnano",
            provincia: "SA",
            provinciaEstesa: "Salerno",
            indirizzo: "Via Francesco Petrarca 4"
        },
        pIva: {
            prefisso: "IT",
            codice: "04408890236"
        }
    },
    cookiePolicy: {
        dataAggiornamento: "marzo 2023"
    },
    privacyPolicy: {
        dataAggiornamento: "marzo 2023"
    },
    settings: {
        backend: {
            baseUrl: "http://localhost:8080/backend/api/"
        },
        security: {
            encodeSessionValues: false,
            token1: "ZXlKaGJHY2lPaUpJVXpJMU5pSXNJblI1Y0NJNklrcFhWQ0o5LmV5SnpkV0lp",
            token2: "T2lJeE1qTTBOVFkzT0Rrd0lpd2libUZ0WlNJNklrcHZhRzRnUkc5bElpd2lhV0YwSWpv"
        },
        permissionRoles: {
            ADM: "ROLE_ADMIN",
            MOD: "ROLE_MODERATOR",
            USR: "ROLE_USER"
        },
        loginSignup: {
            MAX_EMAIL_LEN: 50,
            MAX_PASSWORD_LEN: 50
        }
    },
    support: {
        email: "support@latuacommercialista.it"
    },
    errors: {
        msgTechnicalError: {
            title: "Errore tecnico",
            message: "riprova o contatta l'assistenza se il problema persiste"
        }
    }
}