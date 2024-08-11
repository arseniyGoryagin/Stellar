package com.stellar.data.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import kotlinx.serialization.ExperimentalSerializationApi
import java.io.InputStream
import java.io.OutputStream


@OptIn(ExperimentalSerializationApi::class)
object CardSerializer : Serializer<CardProto> {

    override val defaultValue: CardProto
        get() = CardProto.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): CardProto {
        try{
            return CardProto.parseFrom(input)
        }catch (e : InvalidProtocolBufferException){
            throw CorruptionException("Cannot read from proto", e)
        }
    }

    override suspend fun writeTo(t: CardProto, output: OutputStream) {
        t.writeTo(output)
    }


}