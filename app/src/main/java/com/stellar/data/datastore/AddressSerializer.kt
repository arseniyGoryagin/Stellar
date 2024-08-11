package com.stellar.data.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import kotlinx.serialization.ExperimentalSerializationApi
import java.io.InputStream
import java.io.OutputStream


@OptIn(ExperimentalSerializationApi::class)
object AddressSerializer : Serializer<AddressProto> {

    override val defaultValue: AddressProto
        get() = AddressProto.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): AddressProto {
        try{
            return AddressProto.parseFrom(input)
        }catch (e : InvalidProtocolBufferException){
            throw CorruptionException("Cannot read from proto", e)
        }
    }

    override suspend fun writeTo(t: AddressProto, output: OutputStream) {
        t.writeTo(output)
    }


}