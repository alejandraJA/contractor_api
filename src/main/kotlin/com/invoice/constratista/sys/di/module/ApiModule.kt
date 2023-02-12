package com.invoice.constratista.sys.di.module

import com.invoice.constratista.datasource.repository.web.FacturapiRepositoryImp
import com.invoice.constratista.datasource.web.Service
import com.invoice.constratista.sys.domain.repository.FacturapiRepository
import com.invoice.constratista.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Configuration
class ApiModule {
    @Bean
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Bean
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Constants.BASE_URL)
        .client(okHttpClient)
        .build()

    @Bean
    fun provideService(retrofit: Retrofit): Service = retrofit.create(Service::class.java)

    @Bean
    fun provideFacturapiRepository(facturapiRepositoryImp: FacturapiRepositoryImp): FacturapiRepository =
        facturapiRepositoryImp

}