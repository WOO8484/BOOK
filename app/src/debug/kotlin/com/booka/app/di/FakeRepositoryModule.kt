package com.booka.app.di

import com.booka.core.testing.fake.FakeImportRepository
import com.booka.core.testing.fake.FakeLibraryRepository
import com.booka.core.testing.fake.FakeMetadataRepository
import com.booka.core.testing.fake.FakeReaderRepository
import com.booka.domain.repository.ImportRepository
import com.booka.domain.repository.LibraryRepository
import com.booka.domain.repository.MetadataRepository
import com.booka.domain.repository.ReaderRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * PART 1 전용 DI 바인딩. debug 소스셋에만 존재하므로 release 빌드에는 절대 포함되지 않는다(지시서 7.1, 3.6).
 * PART 2~3에서 :data:* 모듈의 실제 구현체로 교체되면 이 파일은 제거된다.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class FakeRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindLibraryRepository(impl: FakeLibraryRepository): LibraryRepository

    @Binds
    @Singleton
    abstract fun bindImportRepository(impl: FakeImportRepository): ImportRepository

    @Binds
    @Singleton
    abstract fun bindMetadataRepository(impl: FakeMetadataRepository): MetadataRepository

    @Binds
    @Singleton
    abstract fun bindReaderRepository(impl: FakeReaderRepository): ReaderRepository
}
