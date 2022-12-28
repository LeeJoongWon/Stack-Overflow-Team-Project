import styled from 'styled-components';
import InputBox from '../../../components/function/InputBox';
import useInput from '../../../components/hook/useInput';
import Content from './Content';
import axios from 'axios';

const Section = styled.section`
    max-width: 1100px;
    width: calc(100% - 24px);
    height: 100%;
    margin-bottom: 50px;
    display: flex;
    flex-direction: column;
    flex-wrap: nowrap;
    align-items: flex-start;
`;

const H1 = styled.h1`
    font-weight: 100;
    margin: 0 0 1em;
`;

const EnterAnswer = styled.div`
    margin: 20px;
    margin-top: 50px;
    width: 100%;
`;

const ContentList = ({ dataList, dataHandler }) => {
    const questionData = dataList.questionData;
    const answerData = dataList.answerData;

    const sendToServer = (data) => {
        const answer = {
            content: data,
            questionId: questionData.questionId,
            memberId: 1,
        };
        axios
            .post(`http://localhost:8080/answers/`, answer)
            .then((res) => dataHandler.setAnswerData([...answerData, res.data.data]))
            .catch((error) => console.error(error));
    };
    const [inputValue, setInputValue, handleChange, handleSubmit] = useInput('', sendToServer);

    return (
        <Section>
            {/*질문 */}
            <Content key={questionData.questionId} data={questionData} category={'question'} dataHandler={dataHandler.setQuestionData} />
            {/* 답변 */}
            <H1>{answerData.length} Answers</H1>
            {answerData.map((el, index) => (
                <Content key={index} category={'answer'} data={el} dataHandler={dataHandler.setAnswerData} answerData={answerData} index={index} />
            ))}
            {/*질문에 대한 답변 작성*/}
            <EnterAnswer>
                <InputBox
                    title={'Your Answer'}
                    subtitle={' '}
                    OnChange={handleChange}
                    Placeholder={'답변을 입력해주세요'}
                    Value={inputValue}
                    setValue={setInputValue}
                    SubmitBtnName={'Post Your Answer'}
                    Submit={handleSubmit}
                ></InputBox>
            </EnterAnswer>
        </Section>
    );
};

export default ContentList;