/*
 *
 * Copyright 2017-2018 Nitrite author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.dizitart.no2.common.util;

import lombok.experimental.UtilityClass;
import org.dizitart.no2.Document;
import org.dizitart.no2.common.mapper.NitriteMapper;
import org.dizitart.no2.filters.Filter;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import static org.dizitart.no2.common.Constants.DOC_ID;
import static org.dizitart.no2.filters.Filter.eq;

/**
 * A utility class for {@link Document}.
 *
 * @since 1.0
 * @author Anindya Chatterjee
 */
@UtilityClass
public class DocumentUtils {

    private static PodamFactory factory = new PodamFactoryImpl();

    /**
     * Determines whether a document has recently been updated/created than the other.
     *
     * @param recent the recent document
     * @param older  the older document
     * @return the boolean value
     */
    public static boolean isRecent(Document recent, Document older) {
        if (recent.getRevision() == older.getRevision()) {
            return recent.getLastModifiedTime() >= older.getLastModifiedTime();
        }
        return recent.getRevision() > older.getRevision();
    }

    /**
     * Create unique filter to identify the `document`.
     *
     * @param document the document
     * @return the unique filter
     */
    public static Filter createUniqueFilter(Document document) {
        return eq(DOC_ID, document.getId().getIdValue());
    }

    /**
     * Creates an empty document having all fields of a `type`.
     *
     * @param <T>           the type parameter
     * @param nitriteMapper the nitrite mapper
     * @param type          the type
     * @return the document
     */
    public static <T> Document dummyDocument(NitriteMapper nitriteMapper, Class<T> type) {
        T dummy = factory.manufacturePojo(type);
        return nitriteMapper.asDocument(dummy);
    }
}